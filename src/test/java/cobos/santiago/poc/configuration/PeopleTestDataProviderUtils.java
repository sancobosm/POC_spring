package cobos.santiago.poc.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PeopleTestDataProviderUtils {

    public static String supplyValidContent() {
        return """
                person_ID,name,first,last,middle,email,phone,fax,title
                3131,"Smith, John ",John,Smith,,SmithJ@univ.edu,963.555.1234,963.777.5678,Professor
                3132,"Johnson, Mary ",Mary,Johnson,,JohnsonM@univ.edu,963.555.5678,963.777.4321,Associate Professor
                3133,"Garcia, Carlos ",Carlos,Garcia,,GarciaC@univ.edu,963.555.8765,963.777.1234,Assistant Professor
                3134,"Davis, Sarah ",Sarah,Davis,,DavisS@univ.edu,963.555.2345,963.777.8765,Professor
                3135,"Brown, Michael ",Michael,Brown,,BrownM@univ.edu,963.555.6789,963.777.9876,Associate Curator
                3136,"Wilson, Linda ",Linda,Wilson,,WilsonL@univ.edu,963.555.3456,963.777.5432,Curator
                3137,"Martinez, Laura ",Laura,Martinez,,MartinezL@univ.edu,963.555.4567,963.777.7890,Assistant Professor
                3138,"Jones, Robert ",Robert,Jones,,JonesR@univ.edu,963.555.9876,963.777.6543,Professor
                3139,"Lee, Emily ",Emily,Lee,,LeeE@univ.edu,963.555.5432,963.777.8765,Assistant Curator
                3140,"Gonzalez, David ",David,Gonzalez,,GonzalezD@univ.edu,963.555.6789,963.777.4321,Curator""";
    }

}
