package test_framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class STesting {
	
	

	
	public void add(List<STest<?>> tests) {
		
	}
	
	public STesting(List<STest<?>> test_list) {
		JFrame frame = new JFrame("STest");
		JPanel panel = new JPanel();
		panel.setBackground(new Color(40, 40, 40));
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		
		final JTextArea expected = new JTextArea();
		expected.setBackground(new Color(0,0,0));
		expected.setForeground(new Color(250, 250, 250));
		
		final JTextArea actual = new JTextArea();
		actual.setBackground(new Color(0,0,0));
		actual.setForeground(new Color(250, 250, 250));
		
		DefaultListModel<STest<?>> pass_list = new DefaultListModel<>();
		DefaultListModel<STest<?>> fail_list = new DefaultListModel<>();
		for (STest<?> test : test_list) {
			if (test.pass()) {
				pass_list.addElement(test);
			} else {
				fail_list.addElement(test);
			}
		}
		final JList<STest<?>> pass_entries = new JList<>(pass_list);
		final JList<STest<?>> fail_entries = new JList<>(fail_list);
		pass_entries.setBackground(new Color(165,255,165));
		pass_entries.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					STest<?> selection = pass_entries.getSelectedValue();
					expected.setText(selection.expected().toString());
					actual.setText(selection.actual().toString());
				}
			}
		});
		fail_entries.setBackground(new Color(255,165,165));
		fail_entries.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					STest<?> selection = fail_entries.getSelectedValue();
					expected.setText(selection.expected().toString());
					actual.setText(selection.actual().toString());
				}
			}
		});
		
		
		
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGap(5)
					.addGroup(layout.createParallelGroup()
						.addComponent(fail_entries)
						.addComponent(pass_entries)
						)
					.addGap(5)
					.addComponent(expected)
					.addGap(5)
					.addComponent(actual)
					.addGap(5)
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGap(5)
				.addGroup(
					layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
							.addComponent(fail_entries)
							.addGap(5)
							.addComponent(pass_entries)
							)
						.addComponent(expected)
						.addComponent(actual)
					)
				.addGap(5)
				);
		
	//	panel.add(entries);
	//	panel.add(expected);
	//	panel.add(actual);
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		frame.setVisible(true);
	}
	
	
}
