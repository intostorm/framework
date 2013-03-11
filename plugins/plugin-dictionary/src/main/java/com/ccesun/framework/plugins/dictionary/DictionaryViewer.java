package com.ccesun.framework.plugins.dictionary;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ccesun.framework.core.dao.support.PageRequest;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.core.dao.support.QCriteria.Op;
import com.ccesun.framework.core.dao.support.Sort;
import com.ccesun.framework.plugins.dictionary.domain.Dictionary;
import com.ccesun.framework.plugins.dictionary.service.DictionaryService;

public class DictionaryViewer extends JFrame {
	
	private static final long serialVersionUID = 5362865073071387422L;

	private DictionaryService ds;
	
	private JTextField jTextFieldSearchType;
	private JTextField jTextFieldSearchKey;
	private JTextField jTextFieldSearchParentKey;
	private JTextField jTextFieldSearchDictValue0;
	private JTextField jTextFieldSearchDictValue1;
	private JTextField jTextFieldSearchDictValue2;
	private JTextField jTextFieldSearchDictValue3;
	private JButton jButtonSubmit;
	
	private JTable jTableList;
	private JScrollPane jScrollPaneList;
	
	public DictionaryViewer() {
		
		setBounds(100, 100, 750, 450);
		getContentPane().setLayout(new BorderLayout());
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring/root-context.xml", "spring/plugin-dictionary.xml");
		ds = ac.getBean(DictionaryService.class);
		
		this.setTitle("Dictionary Viewer");
		
		JPanel jPanelSearch = new JPanel();
		  
		jTextFieldSearchType = new JTextField("KIND", 5);
		jTextFieldSearchKey = new JTextField(5);
		jTextFieldSearchParentKey = new JTextField(5);
		jTextFieldSearchDictValue0 = new JTextField(5);
		jTextFieldSearchDictValue1 = new JTextField(5);
		jTextFieldSearchDictValue2 = new JTextField(5);
		jTextFieldSearchDictValue3 = new JTextField(5);
		jButtonSubmit = new JButton("查询");
		
		jButtonSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == jButtonSubmit) {
					
					String searchType = jTextFieldSearchType.getText();
					String searchKey = jTextFieldSearchKey.getText();
					String searchParentKey = jTextFieldSearchParentKey.getText();
					String searchDictValue0 = jTextFieldSearchDictValue0.getText();
					String searchDictValue1 = jTextFieldSearchDictValue1.getText();
					String searchDictValue2 = jTextFieldSearchDictValue2.getText();
					String searchDictValue3 = jTextFieldSearchDictValue3.getText();
					
					List<Dictionary> dictList = getList(searchType, searchKey, searchParentKey, searchDictValue0, searchDictValue1, searchDictValue2, searchDictValue3);
					showList(dictList);
				}
			}
		});
		
		jPanelSearch.add(new JLabel("TYPE"));
		jPanelSearch.add(jTextFieldSearchType);
		jPanelSearch.add(new JLabel("KEY"));
		jPanelSearch.add(jTextFieldSearchKey);
		jPanelSearch.add(new JLabel("PKEY"));
		jPanelSearch.add(jTextFieldSearchParentKey);
		jPanelSearch.add(new JLabel("V0"));
		jPanelSearch.add(jTextFieldSearchDictValue0);
		jPanelSearch.add(new JLabel("V1"));
		jPanelSearch.add(jTextFieldSearchDictValue1);
		jPanelSearch.add(new JLabel("V2"));
		jPanelSearch.add(jTextFieldSearchDictValue2);
		jPanelSearch.add(new JLabel("V3"));
		jPanelSearch.add(jTextFieldSearchDictValue3);
		jPanelSearch.add(jButtonSubmit);
		
		getContentPane().add(jPanelSearch, BorderLayout.NORTH);
		
		jTableList = new JTable();
		
		jTableList.setRowHeight(20);
		jTableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		String[] tableHeads = new String[]{"TYPE","KEY","PARENT_KEY","DICT_VALUE0","DICT_VALUE1","DICT_VALUE2","DICT_VALUE3"};
		jScrollPaneList = new JScrollPane(jTableList);
		
		DefaultTableModel dtm = (DefaultTableModel)jTableList.getModel();
		dtm.setColumnIdentifiers(tableHeads);
		
		getContentPane().add(jScrollPaneList, BorderLayout.CENTER);
		
		List<Dictionary> dictList = getList("KIND", null, null, null, null, null, null);
		showList(dictList);
		
		setVisible(true);
		
	}

	private void showList(List<Dictionary> dictList) {
		
		DefaultTableModel dtm = (DefaultTableModel)jTableList.getModel();
		dtm.setRowCount(0);
		
		for (Dictionary dict : dictList) {
			Vector<Object> row = new Vector<Object>();
			row.add(dict.getDictType());
			row.add(dict.getDictKey());
			row.add(dict.getParentKey());
			row.add(dict.getDictValue0());
			row.add(dict.getDictValue1());
			row.add(dict.getDictValue2());
			row.add(dict.getDictValue3());
			
			dtm.addRow(row);
		}
		
		jTableList.repaint();
	}
	
	private List<Dictionary> getList(String type, String key, String parentKey, String value0, String value1, String value2, String value3) {
		QCriteria criteria = new QCriteria();
		if (!StringUtils.isBlank(type)) {
			criteria.addEntry("dictType", Op.LIKE, "%" + type.toUpperCase() + "%");
		}
		if (!StringUtils.isBlank(key)) {
			criteria.addEntry("dictKey", Op.LIKE, "%" + key.toUpperCase() + "%");
		}
		if (!StringUtils.isBlank(parentKey)) {
			criteria.addEntry("parentKey", Op.LIKE, "%" + parentKey.toUpperCase() + "%");
		}
		if (!StringUtils.isBlank(value0)) {
			criteria.addEntry("dictValue0", Op.LIKE, "%" + value0.toUpperCase() + "%");
		}
		if (!StringUtils.isBlank(value1)) {
			criteria.addEntry("dictValue1", Op.LIKE, "%" + value1.toUpperCase() + "%");
		}
		if (!StringUtils.isBlank(value2)) {
			criteria.addEntry("dictValue2", Op.LIKE, "%" + value2.toUpperCase() + "%");
		}
		if (!StringUtils.isBlank(value3)) {
			criteria.addEntry("dictValue3", Op.LIKE, "%" + value3.toUpperCase() + "%");
		}
		
		Sort sort = new Sort().asc("dictKey");
		PageRequest pageRequest = new PageRequest(1, 1000, sort);
		return ds.find(pageRequest, criteria);
	}
	
	public static void main(String[] args) {
		new DictionaryViewer();
	}

}
