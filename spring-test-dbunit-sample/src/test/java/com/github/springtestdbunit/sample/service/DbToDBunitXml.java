package com.github.springtestdbunit.sample.service;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.exporter.DataSetExportConfig;
import com.github.database.rider.core.exporter.DataSetExporter;
import com.github.springtestdbunit.sample.entity.Person;
import com.remondis.resample.Sample;
import com.remondis.resample.Samples;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class DbToDBunitXml {

	@Autowired
	private PersonService personService;

	@Autowired
	DataSource dataSource;

	private Sample<Person> persons;

	@Before
	public void build() {
		this.persons = Samples.Default.of(Person.class).checkForNullFields();
	}

	@Test
	public void testFind() throws Exception {
		Person person = persons.newInstance();
		personService.add(person);

		DataSetExportConfig dataSetExportConfig = new DataSetExportConfig().outputFileName("export.xml")
				.dataSetFormat(DataSetFormat.XML);

		DataSetExporter.getInstance().export(new DatabaseConnection(dataSource.getConnection()), dataSetExportConfig);
	}

}
