package br.ce.wcaquino.dbunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import br.ce.wcaquino.dao.utils.ConnectionFactory;

public class ImportExport {

	public static void main(String[] args) throws Exception {
		//exportarBanco();
		importarBanco();
	}

	private static void importarBanco() throws DatabaseUnitException, SQLException, ClassNotFoundException,
			DataSetException, FileNotFoundException {
		DatabaseConnection dbConn = new DatabaseConnection(ConnectionFactory.getConnection());
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		IDataSet dataSet = builder.build(new FileInputStream("massas" + File.separator + "entrada.xml"));
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
	}

	private static void exportarBanco() throws Exception {
		DatabaseConnection dbConn = new DatabaseConnection(ConnectionFactory.getConnection());
		IDataSet dataSet = dbConn.createDataSet();
		FileOutputStream fos = new FileOutputStream("massas" + File.separator + "saida.xml");
		FlatXmlDataSet.write(dataSet, fos);
	}
}
