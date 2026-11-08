package presentation;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.util.List;
/**
 * Clasa controller care gestioneaza logica dintre view-uri si clasele BLL.
 * Foloseste reflection pentru a genera headerele tabelelor si a le popula cu date.
 */
public class Controller {
    /**
     * Cream un model de tabel dintr-o lista de obiecte folosind reflection.
     * Extragem numele campurilor ca headere si valorile campurilor ca randuri.
     *
     * @param <T> tipul obiectelor din lista
     * @param objects lista de obiecte din care se construieste modelul
     * @return un {@link TableModel} populat cu datele din lista
     */
    public static <T> TableModel createTableModel(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            return new DefaultTableModel();
        }
        Class<?> clazz = objects.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        // Generam headerele tabelului prin reflection
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        // Populam randurile tabelului cu valorile campurilor
        Object[][] data = new Object[objects.size()][fields.length];
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    data[i][j] = fields[j].get(objects.get(i));
                } catch (IllegalAccessException e) {
                    data[i][j] = "";
                }
            }
        }
        return new DefaultTableModel(data, columnNames);
    }
}