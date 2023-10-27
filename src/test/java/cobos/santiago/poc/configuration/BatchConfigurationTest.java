package cobos.santiago.poc.configuration;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.DocFlavor;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.stream.Stream;

@SpringBatchTest
@SpringBootTest(properties = {"input.file=peopleTest.csv"})
@Slf4j
class BatchConfigurationTest {

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Value("${input.file}")
    private String input_file;
    private final static String RESOURCES_PATH = "src/test/resources" + File.separator;


    @SneakyThrows
    @BeforeEach
    void setUp() {
        if (Files.notExists(Path.of(RESOURCES_PATH + input_file))) {
            Files.createFile(Path.of(RESOURCES_PATH + input_file));
        }
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @SneakyThrows
    @AfterEach
    void cleanUp() {
        try (Stream<Path> fileTree = Files.walk(Path.of(RESOURCES_PATH))) {
            fileTree.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .peek(file -> log.warn("Deleting the file: {}", file.getName()))
                    .forEach(File::delete);

        }
    }

    @SneakyThrows
    @Test
    @DisplayName("GIVEN a csv with people WHEN jobLaunched THEN persist into db")
    void shouldReadCsvAndPersistDataIntoDataBase() {
        //GIVEN
        Files.writeString(Path.of(RESOURCES_PATH + input_file), PeopleTestDataProviderUtils.supplyValidContent());
        //WHEN
        /*var jobParameter = new JobParametersBuilder()
                .addString("input.file.name", peopleCsv.toString())
                .addDate("uniqueness", new Date())
                .toJobParameters();*/


        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //THEN
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}