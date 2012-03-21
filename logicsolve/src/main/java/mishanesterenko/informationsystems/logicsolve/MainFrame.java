package mishanesterenko.informationsystems.logicsolve;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import mishanesterenko.informationsystems.logicsolve.domain.Production;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Dimension;

/**
 * @author Michael Nesterenko
 *
 */
public class MainFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8310671238968688038L;
    private JPanel contentPane;
    private JTable productionsTable;
    private JPanel panel;
    private JTextField ifInput;
    private JTextField thenInput;
    private JTextField initialWorkingMemroyInput;
    private ProductionTableModel productionDataSource = new ProductionTableModel(new ArrayList<Production>());
    private JList workingMemroyList;
    private JList conflictsList;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 903, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        
        JPanel productionsContainer = new JPanel();
        productionsContainer.setBorder(new TitledBorder(null, "\u041F\u0440\u043E\u0434\u0443\u043A\u0446\u0438\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        contentPane.add(productionsContainer, gbc_panel);
        productionsContainer.setLayout(new BorderLayout(5, 5));

        JPanel productionsTableHeader = new JPanel(new BorderLayout());
        productionsTable = new JTable();
        productionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productionsTable.setModel(productionDataSource);

        {
            DefaultTableCellRenderer dr = new DefaultTableCellRenderer();
            dr.setHorizontalAlignment(SwingConstants.CENTER);
            TableColumnModel columnModel = productionsTable.getColumnModel();
            for (int colIndex = 0; colIndex < columnModel.getColumnCount(); ++colIndex) {
                TableColumn column = columnModel.getColumn(colIndex);
                
                if (colIndex == 0) {
                    column.setPreferredWidth(10);
                } else {
                    column.setPreferredWidth(100);
                }
                column.setCellRenderer(dr);
            }
        }

        productionsTableHeader.add(productionsTable.getTableHeader(), BorderLayout.NORTH);
        productionsTableHeader.add(new JScrollPane(productionsTable), BorderLayout.CENTER);
        productionsContainer.add(productionsTableHeader, BorderLayout.CENTER);
        
        panel = new JPanel();
        productionsContainer.add(panel, BorderLayout.EAST);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        Box verticalBox = Box.createVerticalBox();

        JButton btnNewButton = new JButton("Вверх");
        btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSelection = productionsTable.getSelectedRow();
                if (currentSelection == -1) {
                    return;
                }

                productionDataSource.moveProductionUp(currentSelection);
                productionsTable.getSelectionModel().setSelectionInterval(currentSelection - 1, currentSelection - 1);
            }
        });
        verticalBox.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Вниз");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSelection = productionsTable.getSelectedRow();
                if (currentSelection == productionsTable.getRowCount() - 1) {
                    return;
                }

                productionDataSource.moveProductionDown(currentSelection);
                if (currentSelection + 1 < productionsTable.getRowCount()) {
                    productionsTable.getSelectionModel().setSelectionInterval(currentSelection + 1, currentSelection + 1);
                }
            }
        });
        btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
        verticalBox.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Удалить");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSelection = productionsTable.getSelectedRow();
                if (currentSelection == -1) {
                    return;
                }

                productionDataSource.removeProduction(currentSelection);
            }
        });
        btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        verticalBox.add(btnNewButton_2);

        panel.add(verticalBox);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "\u0420\u0430\u0431\u043E\u0447\u0430\u044F \u043F\u0430\u043C\u044F\u0442\u044C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 0;
        contentPane.add(panel_1, gbc_panel_1);
        panel_1.setLayout(new BorderLayout(5, 5));
        
        workingMemroyList = new JList();
        panel_1.add(new JScrollPane(workingMemroyList));
        
        JButton btnNewButton_3 = new JButton("Вывод в глубь");
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (workingMemroyList.getModel().getSize() == 0 || !(workingMemroyList.getModel().getElementAt(0) instanceof Set)) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Пожалуйста добавьте начальную память");
                    return;
                }
                List<Production> prods = productionDataSource.getProductionsView();
                @SuppressWarnings("unchecked")
                Set<String> wm = (Set<String>) workingMemroyList.getModel().getElementAt(0);

                Engine engine = new Engine(prods, wm);
                engine.run();
                if (!engine.isSolved()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Не получилось достигнсть целевого элемента");
                }

                workingMemroyList.setModel(new WorkingMemoryListModel(engine));
                conflictsList.setModel(new ConflictsListModel(engine));
            }
        });
        panel_1.add(btnNewButton_3, BorderLayout.SOUTH);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "\u041A\u043E\u043D\u0444\u043B\u0438\u043A\u0442\u044B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.insets = new Insets(0, 0, 5, 0);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 2;
        gbc_panel_2.gridy = 0;
        contentPane.add(panel_2, gbc_panel_2);
        panel_2.setLayout(new BorderLayout(5, 5));
        
        conflictsList = new JList();
        panel_2.add(new JScrollPane(conflictsList));
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u041D\u043E\u0432\u0430\u044F \u043F\u0440\u043E\u0434\u0443\u043A\u0446\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.insets = new Insets(0, 0, 0, 5);
        gbc_panel_3.fill = GridBagConstraints.BOTH;
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 1;
        contentPane.add(panel_3, gbc_panel_3);
        
        JLabel label = new JLabel("Если");
        panel_3.add(label);
        
        ifInput = new JTextField();
        panel_3.add(ifInput);
        ifInput.setColumns(10);
        
        JLabel label_1 = new JLabel("то");
        panel_3.add(label_1);
        
        thenInput = new JTextField();
        panel_3.add(thenInput);
        thenInput.setColumns(10);
        
        JButton button = new JButton("Добавить");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ifPart = ifInput.getText();
                String thenPart = thenInput.getText();
                if (ifPart.length() != 1 || thenPart.length() != 1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Вы должны ввести по одному символу в каждое поле ввода");
                    return;
                }
                Production newProduction = new Production(ifPart.toUpperCase(), thenPart.toUpperCase());
                if (productionDataSource.containsProduction(newProduction)) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Данная продукция уже добавлена");
                    return;
                }

                productionDataSource.addProduction(newProduction);
            }
        });
        panel_3.add(button);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new TitledBorder(null, "\u041D\u0430\u0447\u0430\u043B\u044C\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel_4 = new GridBagConstraints();
        gbc_panel_4.gridwidth = 2;
        gbc_panel_4.insets = new Insets(0, 0, 0, 5);
        gbc_panel_4.fill = GridBagConstraints.BOTH;
        gbc_panel_4.gridx = 1;
        gbc_panel_4.gridy = 1;
        contentPane.add(panel_4, gbc_panel_4);
        FlowLayout fl_panel_4 = new FlowLayout(FlowLayout.LEFT, 5, 5);
        panel_4.setLayout(fl_panel_4);

        Box horizontalBox = Box.createHorizontalBox();
        initialWorkingMemroyInput = new JTextField();
        initialWorkingMemroyInput.setPreferredSize(new Dimension(50, 20));
        horizontalBox.add(initialWorkingMemroyInput);
        initialWorkingMemroyInput.setColumns(30);
        
        JButton btnNewButton_4 = new JButton("Загрузить");
        btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wm = initialWorkingMemroyInput.getText();
                if (!wm.toUpperCase().matches("[A-Z]+")) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Начальная рабочая память должна содержать только буквы английского алфавита");
                    return;
                }
                Set<String> initialWorkingMemory = new HashSet<String>();
                for (int charIndex = 0; charIndex < wm.length(); ++charIndex) {
                    initialWorkingMemory.add(String.valueOf(wm.charAt(charIndex)).toUpperCase());
                }
                DefaultListModel model = new DefaultListModel();
                model.addElement(initialWorkingMemory);
                workingMemroyList.setModel(model);
            }
        });
        horizontalBox.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Очистить");
        btnNewButton_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workingMemroyList.setModel(new DefaultListModel());
                conflictsList.setModel(new DefaultListModel());
            }
        });
        horizontalBox.add(btnNewButton_5);

        panel_4.add(horizontalBox);
    }

}

class ProductionTableModel extends AbstractTableModel {
    public final static String[] COLUMN_NAMES = {"N", "If", "Then"};

    private List<Production> productions; 

    public ProductionTableModel(final List<Production> prods) {
        if (prods == null) {
            throw new NullPointerException();
        }

        productions = prods;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7153546944275283224L;

    @Override
    public int getRowCount() {
        return productions.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex;
        }
        if (columnIndex == 1) {
            return productions.get(rowIndex).getIf();
        }
        if (columnIndex == 2) {
            return productions.get(rowIndex).getThen();
        }
        return null;
    }

    @Override
    public String getColumnName(final int colIndex) {
        return COLUMN_NAMES[colIndex];
    }

    protected boolean moveProduction(final int index, final int direction) {
        if (index + direction < 0 || index + direction >= productions.size()) {
            return false;
        }

        swapItems(productions, index, index + direction);

        fireTableRowsDeleted(Math.max(index, index + direction), Math.max(index, index + direction));
        fireTableRowsDeleted(Math.min(index, index + direction), Math.min(index, index + direction));
        fireTableRowsInserted(Math.min(index, index + direction), Math.min(index, index + direction));
        fireTableRowsInserted(Math.max(index, index + direction), Math.max(index, index + direction));
        return true;
    }

    public void moveProductionUp(final int index) {
        moveProduction(index, -1);
    }

    public void moveProductionDown(final int index) {
        moveProduction(index, +1);
    }

    public void addProduction(final Production p) {
        productions.add(p);
        fireTableRowsInserted(productions.size() - 1, productions.size() - 1);
    }

    public void removeProduction(final int index) {
        productions.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public List<Production> getProductionsView() {
        return Collections.unmodifiableList(productions);
    }

    public boolean containsProduction(final Production p) {
        return productions.contains(p);
    }

    private static <T>void swapItems(final List<T> list, final int i1, final int i2) {
        T temp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, temp);
    }

}

abstract class ResultListModel extends AbstractListModel {
    private Engine engine; 

    public ResultListModel(final Engine e) {
        if (e == null) {
            throw new NullPointerException();
        }

        engine = e;
    }

    protected Engine getEngine() {
        return engine;
    }

    @Override
    public int getSize() {
        return engine.getStepCount();
    }
 
}

class WorkingMemoryListModel extends ResultListModel {

    /**
     * @param e
     */
    public WorkingMemoryListModel(Engine e) {
        super(e);
    }

    @Override
    public Object getElementAt(int index) {
        return getEngine().getWorkingMemory(index);
    }

}

class ConflictsListModel extends ResultListModel {

    /**
     * @param e
     */
    public ConflictsListModel(Engine e) {
        super(e);
    }

    @Override
    public Object getElementAt(int index) {
        return getEngine().getConflicts(index);
    }
}