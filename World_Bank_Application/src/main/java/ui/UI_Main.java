package ui;

import model.analysis_types.Analysis_Type_1;
import model.analysis_types.Analysis_Type_Factory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class UI_Main extends JFrame {

	private static final String[] ANALYSIS_TYPE_DROPDOWN_STRINGS = { "Agricultural Land %",
			"PM2.5 air pollution vs Forest area", "CO2 emissions & GDP per capita", "Forest area %" };
	private static final String[] COUNTRY_DROPDOWN_STRINGS = { "Brazil", "Canada", "China", "France", "USA" };
	private static final String[] VIEWER_DROPDOWN_STRINGS = { "Pie Chart", "Time Series", "Bar Chart", "Scatter Chart",
			"Report" };
	private static final String[] YEAR_DROPDOWN_STRINGS = { "2010", "2011", "2012", "2013", "2014", "2015", "2016",
			"2017", "2018", "2019", "2020", "2021", "2022" };

	private static final int VIEWER_DROPDOWN_PIE_CHART_INDEX = 0;
	private static final int VIEWER_DROPDOWN_TIME_SERIES_INDEX = 1;
	private static final int VIEWER_DROPDOWN_BAR_CHART_INDEX = 2;
	private static final int VIEWER_DROPDOWN_SCATTER_CHART_INDEX = 3;
	private static final int VIEWER_DROPDOWN_REPORT_INDEX = 4;

	public JComboBox<String> country_dropdown;
	public JComboBox<String> start_year_dropdown;
	public JComboBox<String> end_year_dropdown;
	public JComboBox<String> analysis_type_dropdown;
	public JComboBox<String> viewer_dropdown;

	private JPanel centre_panel;
	private JTextArea report_text_area;

	private HashSet<Integer> viewers_added; // Key = viewer index of dropdown menu

	public UI_Main() {
		initialize_UI();
		add_top_panel_to_UI(setup_country_settings_panel(), setup_year_settings_panel());
		add_centre_panel_to_UI();
		add_bottom_panel_to_UI(setup_view_settings_panel(), setup_analysis_settings_panel());
		finalize_UI();
	}

	private String get_selected_country_name() {
		return (String) country_dropdown.getSelectedItem();
	}

	private int get_selected_start_year() {
		return Integer.parseInt((String) start_year_dropdown.getSelectedItem());
	}

	private int get_selected_end_year() {
		return Integer.parseInt((String) end_year_dropdown.getSelectedItem());
	}

	private void initialize_UI() {
		setTitle("Country Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 900));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}

	private void add_top_panel_to_UI(JPanel country_settings_panel, JPanel year_settings_panel) {
		JPanel p = new JPanel(new FlowLayout());
		p.add(country_settings_panel);
		p.add(year_settings_panel);
		add(p, BorderLayout.NORTH);
	}

	private void add_centre_panel_to_UI() {
		centre_panel = new JPanel(new GridLayout(2, 0, 5, 5));
		add(centre_panel, BorderLayout.CENTER);
//		JScrollPane scrollPane = new JScrollPane(centre_panel);
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		add(scrollPane, BorderLayout.CENTER);
	}

	private void add_bottom_panel_to_UI(JPanel view_settings_panel, JPanel analysis_settings_panel) {
		JPanel p = new JPanel(new BorderLayout());
		p.add(view_settings_panel, BorderLayout.WEST);
		p.add(analysis_settings_panel, BorderLayout.EAST);
		add(p, BorderLayout.SOUTH);
	}

	private void finalize_UI() {
		pack();
		setVisible(true);
	}

	private JPanel setup_country_settings_panel() {
		country_dropdown = new JComboBox<>(COUNTRY_DROPDOWN_STRINGS);
		country_dropdown.setSelectedIndex(1);
		country_dropdown.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				try {
					ArrayList<String> valid_countries = iterate_valid_countries();
					// Valid countries available for data fetching are: Canada, China, USA.
					if (!valid_countries.contains(String.valueOf(country_dropdown.getSelectedItem()))) {
						popup_error_dialog("Data fetching not available for selected country");
						country_dropdown.setSelectedIndex(-1);
					} else {
						// TODO: Fetch data for selected country.
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("Choose a country: "));
		p.add(country_dropdown);

		return p;
	}

	private JPanel setup_year_settings_panel() {
		start_year_dropdown = new JComboBox<>(YEAR_DROPDOWN_STRINGS);
		end_year_dropdown = new JComboBox<>(YEAR_DROPDOWN_STRINGS);

		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("From:"));
		p.add(start_year_dropdown);
		p.add(new JLabel("To: "));
		p.add(end_year_dropdown);

		return p;
	}

	private JPanel setup_view_settings_panel() {
		viewer_dropdown = new JComboBox<>(VIEWER_DROPDOWN_STRINGS);

		viewers_added = new HashSet<>();
		JButton pb = new JButton("+");
		JButton mb = new JButton("-");

		pb.addActionListener(e -> {
			int selected_viewer_index = viewer_dropdown.getSelectedIndex();

			if (!viewers_added.contains(selected_viewer_index)) {
				viewers_added.add(selected_viewer_index);
				System.out.println(viewers_added);
			}
		});
		mb.addActionListener(e -> {
			int selected_viewer_index = viewer_dropdown.getSelectedIndex();

			if (viewers_added.contains(selected_viewer_index)) {
				viewers_added.remove(selected_viewer_index);
				System.out.println(viewers_added);
			}
		});

		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("Available Views: "));
		p.add(viewer_dropdown);
		p.add(pb);
		p.add(mb);

		return p;
	}

	private JPanel setup_analysis_settings_panel() {
		analysis_type_dropdown = new JComboBox<>(ANALYSIS_TYPE_DROPDOWN_STRINGS);

		JButton recalculate_btn = new JButton("Recalculate");
		recalculate_btn.addActionListener(e -> {
			centre_panel.removeAll();
			refresh_UI();

			int selected_analysis_type = analysis_type_dropdown.getSelectedIndex() + 1;
			switch (selected_analysis_type) {
			case 1 -> dispatch_analysis_type_1();
			case 2 -> dispatch_analysis_type_2();
			case 3 -> dispatch_analysis_type_3();
			case 4 -> dispatch_analysis_type_4();
			}
		});

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel("Choose analysis method: "));
		panel.add(analysis_type_dropdown);
		panel.add(recalculate_btn);

		return panel;
	}

	private JPanel setup_chart_panel(JFreeChart chart) {
		ChartPanel p = new ChartPanel(chart);
		p.setPreferredSize(new Dimension(400, 300));
		p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		p.setBackground(Color.WHITE);
		return p;
	}

	private void dispatch_analysis_type_1() {
		double[][] analysis_results = Analysis_Type_Factory.create_analysis(1).perform(get_selected_country_name(),
				get_selected_start_year(), get_selected_end_year());

		if (analysis_results == null) {
			popup_error_dialog("Insufficient data for selected years to perform this analysis method.");
			return;
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_PIE_CHART_INDEX)) {

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(analysis_results[0][0], "Average", "Agricultural land (as % of land area)");
			dataset.addValue(analysis_results[0][1], "Rest", "Agricultural land (as % of land area)");

			JFreeChart pieChart = ChartFactory.createMultiplePieChart("Agricultural land (as % of land area)", dataset,
					TableOrder.BY_COLUMN, true, true, false);

			ChartPanel chartPanel = new ChartPanel(pieChart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);

			centre_panel.add(setup_chart_panel(pieChart));
			refresh_UI();

		}

	}

	private void dispatch_analysis_type_2() {
		double[][] analysis_results = Analysis_Type_Factory.create_analysis(2).perform(get_selected_country_name(),
				get_selected_start_year(), get_selected_end_year());

		if (analysis_results == null) {
			popup_error_dialog("Insufficient data for selected years to perform this analysis method.");
			return;
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_BAR_CHART_INDEX)) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

			for (int i = 0; i < analysis_results.length; i++) {

				dataset.setValue(Math.round(analysis_results[i][1] * 100.0) / 100.0, "PM2.5 air pollution",
						String.valueOf((int) (analysis_results[i][0])));

			}

			for (int i = 0; i < analysis_results.length; i++) {

				dataset2.setValue(Math.round(analysis_results[i][3] * 100.0) / 100.0, "Forest area",
						String.valueOf((int) (analysis_results[i][2])));

			}

			CategoryPlot plot = new CategoryPlot();
			BarRenderer barrenderer1 = new BarRenderer();
			BarRenderer barrenderer2 = new BarRenderer();
			plot.setDataset(0, dataset);
			plot.setRenderer(0, barrenderer1);

			CategoryAxis domain_axis = new CategoryAxis("Year");
			plot.setDomainAxis(domain_axis);

			NumberAxis rangeAxis = new NumberAxis("% Annual Change");
			rangeAxis.setInverted(true);
			plot.setRangeAxis(rangeAxis);

			plot.setDataset(1, dataset2);
			plot.setRenderer(1, barrenderer2);

			NumberAxis rangeAxis1 = new NumberAxis("% Annual Change");
			rangeAxis1.setInverted(true);
			plot.setRangeAxis(1, rangeAxis1);

			plot.mapDatasetToRangeAxis(0, 0);
			plot.mapDatasetToRangeAxis(1, 1);

			JFreeChart bar_chart = new JFreeChart("PM2.5 air pollution/Forest area",
					new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

			ChartPanel chartPanel = new ChartPanel(bar_chart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);

			centre_panel.add(setup_chart_panel(bar_chart));
			refresh_UI();
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_SCATTER_CHART_INDEX)) {

			TimeSeries PM25 = new TimeSeries("PM2.5 air pollution");
			TimeSeries forest_area = new TimeSeries("Forest area");

			for (int i = 0; i < analysis_results.length; i++) {

				PM25.add(new Year((int) (analysis_results[i][0])), Math.round(analysis_results[i][1] * 100.0) / 100.0);

			}

			for (int i = 0; i < analysis_results.length; i++) {

				forest_area.add(new Year((int) (analysis_results[i][2])),
						Math.round(analysis_results[i][3] * 100.0) / 100.0);

			}

			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(PM25);

			TimeSeriesCollection dataset2 = new TimeSeriesCollection();
			dataset2.addSeries(forest_area);

			XYPlot plot = new XYPlot();
			XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
			XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

			plot.setDataset(0, dataset);
			plot.setRenderer(0, itemrenderer1);

			DateAxis domain_axis = new DateAxis("Year");
			plot.setDomainAxis(domain_axis);

			NumberAxis rangeAxis = new NumberAxis("% Annual Change");
			plot.setRangeAxis(rangeAxis);

			plot.setDataset(1, dataset2);
			plot.setRenderer(1, itemrenderer2);

			NumberAxis rangeAxis1 = new NumberAxis("% Annual Change");
			plot.setRangeAxis(1, rangeAxis1);

			plot.mapDatasetToRangeAxis(0, 0);
			plot.mapDatasetToRangeAxis(1, 1);

			JFreeChart scatter_chart = new JFreeChart("PM2.5 air pollution vs Forest area",
					new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

			centre_panel.add(setup_chart_panel(scatter_chart));
			refresh_UI();
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_TIME_SERIES_INDEX)) {

			TimeSeries PM25 = new TimeSeries("PM2.5 air pollution");
			TimeSeries forest_area = new TimeSeries("Forest area");

			for (int i = 0; i < analysis_results.length; i++) {

				PM25.add(new Year((int) (analysis_results[i][0])), Math.round(analysis_results[i][1] * 100.0) / 100.0);

			}

			for (int i = 0; i < analysis_results.length; i++) {

				forest_area.add(new Year((int) (analysis_results[i][2])),
						Math.round(analysis_results[i][3] * 100.0) / 100.0);

			}

			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(PM25);

			TimeSeriesCollection dataset2 = new TimeSeriesCollection();
			dataset2.addSeries(forest_area);

			XYPlot plot = new XYPlot();
			XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
			XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

			plot.setDataset(0, dataset);
			plot.setRenderer(0, splinerenderer1);
			DateAxis domainAxis = new DateAxis("Year");
			plot.setDomainAxis(domainAxis);
			plot.setRangeAxis(new NumberAxis("% Annual Change"));

			plot.setDataset(1, dataset2);
			plot.setRenderer(1, splinerenderer2);
			plot.setRangeAxis(1, new NumberAxis("% Annual Change"));

			plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
			plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

			JFreeChart chart = new JFreeChart("PM2.5 air pollution vs Forest area",
					new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);

			centre_panel.add(setup_chart_panel(chart));
			refresh_UI();
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_REPORT_INDEX)) {

			JTextArea report = new JTextArea();
			report.setEditable(false);
			report.setPreferredSize(new Dimension(400, 300));
			report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			report.setBackground(Color.white);

			StringBuilder sb = new StringBuilder();
			sb.append("PM2.5 air pollution vs Forest area\n");
			sb.append("================================\n");

			for (int i = 0; i < analysis_results.length; i++) {
				sb.append(String.format("# Year %d\n", (int) (analysis_results[i][0])));
				sb.append(String.format("PM2.5 air pollution Annual Change %% : %f\n", analysis_results[i][1]));
				sb.append(String.format("Forest area Annual Change %% : %f\n", analysis_results[i][3]));

				sb.append("\n");

			}

			Font font = new Font("Arial", Font.BOLD, 11);
			report.setText(sb.toString());
			report.setFont(font);
			JScrollPane outputScrollPane = new JScrollPane(report);

			centre_panel.add(outputScrollPane);
			refresh_UI();
		}
	}

	private void dispatch_analysis_type_3() {

		double[][] analysis_results = Analysis_Type_Factory.create_analysis(3).perform(get_selected_country_name(),
				get_selected_start_year(), get_selected_end_year());

		if (analysis_results == null) {
			popup_error_dialog("Insufficient data for selected years to perform this analysis method.");
			return;
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_BAR_CHART_INDEX)) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			for (int i = 0; i < analysis_results.length; i++) {

				for (int j = 0; j < analysis_results[i].length; j++) {
					dataset.setValue(analysis_results[i][1], "C02 emissions/GDP per capital",
							String.valueOf((int) (analysis_results[i][0])));
				}
			}

			CategoryPlot plot = new CategoryPlot();
			plot.setRenderer(0, new BarRenderer());

			CategoryAxis domain_axis = new CategoryAxis("Year");
			plot.setDomainAxis(domain_axis);

			plot.setRangeAxis(new NumberAxis("% Change"));
			plot.setDataset(0, dataset);
			plot.mapDatasetToRangeAxis(0, 0);

			JFreeChart bar_chart = new JFreeChart("C02 emissions/GDP per capital",
					new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

			ChartPanel chartPanel = new ChartPanel(bar_chart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);

			centre_panel.add(setup_chart_panel(bar_chart));
			refresh_UI();
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_SCATTER_CHART_INDEX)) {

			TimeSeriesCollection dataset = new TimeSeriesCollection();
			TimeSeries series1 = new TimeSeries("C02 emissions/GDP per capital");
			for (int i = 0; i < analysis_results.length; i++) {
				series1.add(new Year((int) (analysis_results[i][0])), analysis_results[i][1]);

			}
			dataset.addSeries(series1);

			XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);

			XYPlot plot = new XYPlot();
			plot.setDataset(0, dataset);
			plot.setRenderer(0, itemrenderer1);
			DateAxis domainAxis = new DateAxis("Year");
			plot.setDomainAxis(domainAxis);
			plot.setRangeAxis(new NumberAxis(""));

			plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
			plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

			JFreeChart scatterChart = new JFreeChart("C02 emissions/GDP per capital",
					new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

			ChartPanel chartPanel = new ChartPanel(scatterChart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);

			centre_panel.add(setup_chart_panel(scatterChart));
			refresh_UI();

		}

		if (viewers_added.contains(VIEWER_DROPDOWN_TIME_SERIES_INDEX)) {

			TimeSeries series1 = new TimeSeries("C02 emissions/GDP per capital");

			for (int i = 0; i < analysis_results.length; i++) {
				series1.add(new Year((int) (analysis_results[i][0])),
						Math.round(analysis_results[i][1] * 100.0) / 100.0);

			}

			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(series1);

			XYPlot plot = new XYPlot();
			XYSplineRenderer splinerenderer = new XYSplineRenderer();

			plot.setDataset(dataset);
			plot.setRenderer(splinerenderer);

			DateAxis domainAxis = new DateAxis("Year");
			plot.setDomainAxis(domainAxis);
			plot.setRangeAxis(new NumberAxis("Change %"));

			plot.mapDatasetToRangeAxis(0, 0);

			JFreeChart chart = new JFreeChart("C02 emissions/GDP per capital",
					new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);
			centre_panel.add(setup_chart_panel(chart));
			refresh_UI();
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_REPORT_INDEX)) {

			JTextArea report = new JTextArea();
			report.setEditable(false);
			report.setPreferredSize(new Dimension(400, 300));
			report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			report.setBackground(Color.white);

			StringBuilder sb = new StringBuilder();
			sb.append("C02 emissions/GDP per capital\n");
			sb.append("================================\n");

			for (int i = 0; i < analysis_results.length; i++) {
				sb.append(String.format("# Year %d\n", (int) (analysis_results[i][0])));
				sb.append(String.format("C02 emissions/GDP per capital: %f\n", analysis_results[i][1]));

				sb.append("\n");

			}

			Font font = new Font("Arial", Font.PLAIN, 11);
			report.setText(sb.toString());
			report.setFont(font);
			JScrollPane outputScrollPane = new JScrollPane(report);

			centre_panel.add(outputScrollPane);
			refresh_UI();
		}

	}

	private void dispatch_analysis_type_4() {

		double[][] analysis_results = Analysis_Type_Factory.create_analysis(4).perform(get_selected_country_name(),
				get_selected_start_year(), get_selected_end_year());

		if (analysis_results == null) {
			popup_error_dialog("Insufficient data for selected years to perform this analysis method.");
			return;
		}

		if (viewers_added.contains(VIEWER_DROPDOWN_PIE_CHART_INDEX)) {

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.addValue(analysis_results[0][0], "Average", "Forest area (as % of land area)");
			dataset.addValue(analysis_results[0][1], "Rest", "Forest area (as % of land area)");

			JFreeChart pieChart = ChartFactory.createMultiplePieChart("Forest area (as % of land area)", dataset,
					TableOrder.BY_COLUMN, true, true, false);

			ChartPanel chartPanel = new ChartPanel(pieChart);
			chartPanel.setPreferredSize(new Dimension(400, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);

			centre_panel.add(setup_chart_panel(pieChart));
			refresh_UI();

		}

	}

	private void refresh_UI() {
		revalidate();
		repaint();
	}

	private void popup_error_dialog(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public ArrayList<String> iterate_valid_countries() {
		ArrayList<String> valid_countries = new ArrayList<>();

		// Invoking Iterator Design Pattern
		CountriesIterator it = new CountriesIterator();
		while (it.hasNext()) {
			valid_countries.add((String) it.next());
		}

		return valid_countries;
	}

	public static JSONArray import_JSON_array() {
		try {
			// Parsing json since array of valid countries are stored in a json file.
			Object obj = new JSONParser().parse(new FileReader("countries.json"));
			JSONObject jo = (JSONObject) obj;
			return (JSONArray) jo.get("countries");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new UI_Main();
	}

}
