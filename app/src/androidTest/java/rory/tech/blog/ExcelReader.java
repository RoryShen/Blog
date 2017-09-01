package rory.tech.blog;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Rory on 2017/09/01   .
 */

public class ExcelReader {
    private transient Collection<Object[]> data = null;

    // 从传入的文件流里读取excel内容，并放到data集合数组里。

    public ExcelReader(final InputStream excelInputStream) throws IOException {
        this.data = loadFromSpreadsheet(excelInputStream);
    }

    // put the data in collection and return the data.
    public Collection<Object[]> getData() {
        return data;
    }

    //从文件流中读取sheet里的信息，并放到一个Object数组里
    private Collection<Object[]> loadFromSpreadsheet(final InputStream excelFile)
            throws IOException {
        //把excel解析成POIFSFileSystem文件
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

        data = new ArrayList<Object[]>();
        //通过给定的下标获取一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        //统计列的数量
        int numberOfColumns = countNonEmptyColumns(sheet);
        List<Object[]> rows = new ArrayList<Object[]>();
        List<Object> rowData = new ArrayList<Object>();
        //遍历解析sheet里的数据
        for (Row row : sheet) {
            //如果row里的为空，则退出循环
            if (isEmpty(row)) {
                break;
            } else {
                rowData.clear();
                //循环遍历所有列
                for (int column = 0; column < numberOfColumns; column++) {
                    //读取所有列里的内容
                    Cell cell = row.getCell(column);

                    rowData.add(objectFrom(workbook, cell));
                }
                rows.add(rowData.toArray());
            }
        }
        return rows;
    }

    //判断Row的内容是否为空
    private boolean isEmpty(final Row row) {
        Cell firstCell = row.getCell(0);
        boolean rowIsEmpty = (firstCell == null)
                || (firstCell.getCellTypeEnum() == CellType.BLANK);
        return rowIsEmpty;
    }

    /**
     * Count the number of columns, using the number of non-empty cells in the
     * first row.
     */
    private int countNonEmptyColumns(final Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        return firstEmptyCellPosition(firstRow);
    }

    private int firstEmptyCellPosition(final Row cells) {
        int columnCount = 0;
        for (Cell cell : cells) {
            if (cell.getCellTypeEnum() == CellType.BLANK) {
                break;
            }
            columnCount++;
        }
        return columnCount;
    }


    private Object objectFrom(final HSSFWorkbook workbook, final Cell cell) {
        Object cellValue = null;
        //判断对应cell（单元格）的值，并读取出来。
        if (cell.getCellTypeEnum() == CellType.STRING) {
            cellValue = cell.getRichStringCellValue().getString();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            cellValue = getNumericCellValue(cell);
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            cellValue = cell.getBooleanCellValue();
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            cellValue = evaluateCellFormula(workbook, cell);
        }

        return cellValue;

    }

    //从单元格中读取数字的值
    private Object getNumericCellValue(final Cell cell) {
        Object cellValue;
        if (DateUtil.isCellDateFormatted(cell)) {
            cellValue = new Date(cell.getDateCellValue().getTime());
        } else {
            cellValue = cell.getNumericCellValue();
        }
        return cellValue;
    }

    //判断单元格的格式类型
    private Object evaluateCellFormula(final HSSFWorkbook workbook, final Cell cell) {
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        Object result = null;

        if (cellValue.getCellTypeEnum() == CellType.BOOLEAN) {
            result = cellValue.getBooleanValue();
        } else if (cellValue.getCellTypeEnum() == CellType.NUMERIC) {
            result = cellValue.getNumberValue();
        } else if (cellValue.getCellTypeEnum() == CellType.STRING) {
            result = cellValue.getStringValue();
        }

        return result;
    }
}
