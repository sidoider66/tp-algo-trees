package trees;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class TreePerformance extends JFrame {
    public TreePerformance(String title) {
        super(title);
    }

    private XYSeriesCollection createDataset() {
        XYSeries abrSeries = new XYSeries("ABR");
        XYSeries avlSeries = new XYSeries("AVL");
        int[] sizes = {1000,2000,3000,4000, 5000,6000,7000,8000,9000, 10000};
        Random random = new Random();

        // Create a table model to hold the results
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Taille", "ABR (ms)", "AVL (ms)"}, 0);

        for (int size : sizes) {
            int[] values = random.ints(size, 1, size * 10).toArray();
            int searchValue = values[random.nextInt(size)];

            // Build BST and measure time
            BinarySearchTree bst = new BinarySearchTree();
            for (int value : values) {
                bst.insert(value);
            }
            long startTime = System.nanoTime();
            bst.search(searchValue);
            long endTime = System.nanoTime();
            long bstSearchTime = endTime - startTime;

            // Build AVL and measure time
            AVLTree avlTree = new AVLTree();
            avlTree.buildAVL(values);
            startTime = System.nanoTime();
            avlTree.search(searchValue);
            endTime = System.nanoTime();
            long avlSearchTime = endTime - startTime;

            // Add data to series
            abrSeries.add(size, bstSearchTime / 1_000_000.0); // Convert to milliseconds
            avlSeries.add(size, avlSearchTime / 1_000_000.0); // Convert to milliseconds

            // Add row to the table model
            tableModel.addRow(new Object[]{size, bstSearchTime / 1_000_000.0, avlSearchTime / 1_000_000.0});
        }

        // Create the dataset for the chart
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(abrSeries);
        dataset.addSeries(avlSeries);
        
        // Create the table
        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // Create a panel to hold both chart and table
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createChartPanel(dataset), BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Set the main panel as content pane
        setContentPane(mainPanel);
        return dataset;
    }

    private JPanel createChartPanel(XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Search Time Comparison",
                "Number of Elements",
                "Time (ms)",
                dataset
        );
        return new ChartPanel(chart);
    }

    public void display() {
        createDataset(); // This will initialize and set the content
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TreePerformance example = new TreePerformance("Tree Performance");
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.display();
            example.pack();
            example.setVisible(true);
        });
    }
}