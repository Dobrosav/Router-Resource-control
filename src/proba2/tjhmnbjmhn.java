package proba2;
import com.ireasoning.protocol.snmp.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Label;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JTree;
public class tjhmnbjmhn {
	public static int time=0;
	public static String oid5sec=".1.3.6.1.4.1.9.9.109.1.1.1.1.3.1";
	public static String oid1min=".1.3.6.1.4.1.9.9.109.1.1.1.1.4.1";
	public static String oid5min=".1.3.6.1.4.1.9.9.109.1.1.1.1.5.1";
	public static String oidname=".1.3.6.1.4.1.9.9.48.1.1.1.2.1";
	public static String oidused=".1.3.6.1.4.1.9.9.48.1.1.1.5.1";
	public static String oidfree=".1.3.6.1.4.1.9.9.48.1.1.1.6.1";
	public static String odname2=".1.3.6.1.4.1.9.9.48.1.1.1.2.2"; 
	public static String oidused2=".1.3.6.1.4.1.9.9.48.1.1.1.5.2";
	public static String oidfree2=".1.3.6.1.4.1.9.9.48.1.1.1.6.2";
	private JFrame frame;
	private int vreme;
	private JLabel lblLblname2;
	private JTextField ipadresa;
	private final Label lblUtil = new Label("CPU5sec");
	private JTextField vremeOsv;
	private static boolean radi=true;
	private JLabel labelime;
	private JLabel lblname;
	private JLabel lblNewLabel;
	private JLabel lblLblfree;
	private JLabel lblBussy;
	private JPanel panel;
	private JFreeChart cp;
	private XYSeriesCollection dataset;
	private XYSeries series = new XYSeries("Cpu5sec");
	private XYSeries series1 = new XYSeries("Cpu1min");
	private XYSeries series2 = new XYSeries("CPU5min");
	private ChartPanel myChart;
	private JLabel lblFree2;
	private JLabel lblBussy2;
	/**
	 * Launch the application.
	 */
	//SnmpTarget t = new SnmpTarget("192.168.10.1", 161, "si2019", "si2019", SnmpConst.SNMPV2);
	private class Pratilac extends Thread{
		private SnmpTarget t;
		private SnmpSession s;
		private String cekanje;
		public Pratilac(String dest, String vCekanja) {
			t=new SnmpTarget(dest,161,"si2019", "si2019", SnmpConst.SNMPV2);
			try {
				this.cekanje=vCekanja;
				s=new SnmpSession(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public int utill(SnmpPdu pdu) {
			String st=pdu.toString();
			String str[]=st.split("\n");
			String values[]=str[2].split(": ");
			int CpuUtill=Integer.parseInt(values[1]);
			return CpuUtill;
		}
		public String utillMem(SnmpPdu pdu) {
			String st=pdu.toString();
			String str[]=st.split("\n");
			String values[]=str[2].split(": ");
			String CpuUtill=values[1];
			return CpuUtill;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				while(tjhmnbjmhn.radi) {
					SnmpPdu pdu=s.snmpGetRequest(tjhmnbjmhn.oid5sec);
					int uttill=utill(pdu);
					series.addOrUpdate(tjhmnbjmhn.time, uttill);
					pdu=s.snmpGetRequest(tjhmnbjmhn.oid1min);
					uttill=utill(pdu);
					series1.addOrUpdate(tjhmnbjmhn.time, uttill);
					pdu=s.snmpGetRequest(tjhmnbjmhn.oid5min);
					uttill=utill(pdu);
					series2.addOrUpdate(tjhmnbjmhn.time, uttill);
					pdu=s.snmpGetRequest(tjhmnbjmhn.oidname);
					lblname.setText(utillMem(pdu));
					pdu=s.snmpGetRequest(tjhmnbjmhn.oidfree);
					lblLblfree.setText(utillMem(pdu));
					pdu=s.snmpGetRequest(tjhmnbjmhn.oidused);
					lblBussy.setText(utillMem(pdu));
					pdu=s.snmpGetRequest(tjhmnbjmhn.odname2);
					lblLblname2.setText(utillMem(pdu));
					pdu=s.snmpGetRequest(tjhmnbjmhn.oidfree2);
					lblFree2.setText(utillMem(pdu));
					pdu=s.snmpGetRequest(tjhmnbjmhn.oidused2);
					lblBussy2.setText(utillMem(pdu));
					tjhmnbjmhn.time=tjhmnbjmhn.time+vreme;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tjhmnbjmhn window = new tjhmnbjmhn();
					window.frame.setVisible(true);
				//	new ChartTest().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public tjhmnbjmhn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		ipadresa = new JTextField();
		ipadresa.setBounds(174, 57, 124, 19);
		frame.getContentPane().add(ipadresa);
		ipadresa.setColumns(10);
		
		JButton btnPrati = new JButton("Prati");
		btnPrati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tjhmnbjmhn.radi=true;
				int sleep;
				String dest=ipadresa.getText();
				String vCekanja=vremeOsv.getText();
				Pratilac p=new Pratilac(dest, vCekanja);
				sleep=Integer.parseInt(vCekanja);
				vreme=sleep;
				p.start();
				try {
					Thread.sleep(sleep*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 			}
		});
		btnPrati.setBounds(184, 101, 114, 25);
		frame.getContentPane().add(btnPrati);
		
		JLabel lblIpAdresa = new JLabel("Ip adresa");
		lblIpAdresa.setBounds(102, 59, 66, 15);
		frame.getContentPane().add(lblIpAdresa);
		
		JLabel lblVremeOsvezavanja = new JLabel("Vreme osvezavanja");
		lblVremeOsvezavanja.setBounds(316, 59, 170, 15);
		frame.getContentPane().add(lblVremeOsvezavanja);
		
		vremeOsv = new JTextField();
		vremeOsv.setBounds(454, 57, 124, 19);
		frame.getContentPane().add(vremeOsv);
		vremeOsv.setColumns(10);
		
		JButton btnZaustavi = new JButton("Zaustavi");
		btnZaustavi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tjhmnbjmhn.radi=false;
				lblname.setText("Ime");
				lblLblfree.setText("0");
				lblBussy.setText("0");
				lblLblname2.setText("ime");
				lblFree2.setText("0");
				lblBussy2.setText("0");
				tjhmnbjmhn.time=0;
				
			}
		});
		btnZaustavi.setBounds(464, 101, 114, 25);
		frame.getContentPane().add(btnZaustavi);
		
		labelime = new JLabel("Ime pool-a");
		labelime.setBounds(136, 516, 99, 42);
		frame.getContentPane().add(labelime);
		
		lblname = new JLabel("Ime");
		lblname.setBounds(152, 462, 76, 42);
		frame.getContentPane().add(lblname);
		
		lblNewLabel = new JLabel("Kolicina slobodne memorije");
		lblNewLabel.setBounds(257, 462, 205, 42);
		frame.getContentPane().add(lblNewLabel);
		
		lblLblfree = new JLabel("0");
		lblLblfree.setBounds(305, 516, 99, 42);
		frame.getContentPane().add(lblLblfree);
		
		JLabel lblKolicinaZauzeteMemorije = new JLabel("Kolicina zauzete memorije");
		lblKolicinaZauzeteMemorije.setBounds(495, 476, 205, 15);
		frame.getContentPane().add(lblKolicinaZauzeteMemorije);
		
		lblBussy = new JLabel("0");
		lblBussy.setBounds(555, 516, 99, 42);
		frame.getContentPane().add(lblBussy);
		
	//	lblCpumin = new JLabel("CPU5min");
		//lblCpumin.setBounds(143, 259, 66, 15);
	//	frame.getContentPane().add(lblCpumin);
		
		//panel = new JPanel();
	//	panel.setBackground(Color.black);
		//panel.setBounds(79, 151, 545, 173);
		//frame.getContentPane().add(panel);
		//panel.setLayout(null);
		dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		cp=ChartFactory.createXYLineChart("CPU", "Vreme", "Vrednost", dataset,PlotOrientation.VERTICAL,true,true,false);
	//	cp.setBackgroundImage(cp.DEFAULT_BACKGROUND_IMAGE);
		myChart = new ChartPanel(cp);
		myChart.setVisible(true);
		myChart.setBounds(12, 138, 688, 326);
		myChart.setPreferredSize(new Dimension(400,100));
		myChart.setMouseWheelEnabled(true);
		//panel.add(myChart,BorderLayout.CENTER);
	//	panel.validate();
		frame.getContentPane().add(myChart);
		
		lblLblname2 = new JLabel("ime pool-a");
		lblLblname2.setBounds(136, 596, 99, 34);
		frame.getContentPane().add(lblLblname2);
		
		lblFree2 = new JLabel("0");
		lblFree2.setBounds(305, 596, 94, 25);
		frame.getContentPane().add(lblFree2);
		
		lblBussy2 = new JLabel("0");
		lblBussy2.setBounds(560, 596, 76, 34);
		frame.getContentPane().add(lblBussy2);
	}
}
