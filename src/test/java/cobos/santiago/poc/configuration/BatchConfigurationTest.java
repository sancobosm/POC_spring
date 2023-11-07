package cobos.santiago.poc.configuration;

import cobos.santiago.poc.PocSpringBatchApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@SpringBatchTest
@ContextConfiguration(classes = { PocSpringBatchApplication.class })
@Slf4j
class BatchConfigurationTest {

    private static final Path DIRECTORY_TEST_CSV = Path.of("target/test-classes/resources");
    private static final Path PEOPLE_TEST_CSV = Path.of("target/test-classes/resources/peopleTest.csv");

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @SneakyThrows
    @BeforeEach
    void setUp() {
        if (Files.notExists(DIRECTORY_TEST_CSV)) {
            Files.createDirectory(DIRECTORY_TEST_CSV);
        }
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @SneakyThrows
    @AfterEach
    void cleanUp() {
        try (Stream<Path> fileTree = Files.walk(DIRECTORY_TEST_CSV)) {
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
        Path shouldSucceessCsvPath = Path.of(DIRECTORY_TEST_CSV + File.separator + "people-test-should-success.csv");
        var peopleCsv = Files.createFile(shouldSucceessCsvPath);
        Files.writeString(peopleCsv, PeopleTestDataProviderUtils.supplyValidContent());

        //WHEN
       /* var jobParameter = new JobParametersBuilder()
                .addString("input.file.name", peopleCsv.toString())
                .addDate("uniqueness", new Date())
                .toJobParameters();*/

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //THEN
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}