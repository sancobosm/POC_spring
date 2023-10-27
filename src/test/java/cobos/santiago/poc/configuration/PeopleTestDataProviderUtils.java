package cobos.santiago.poc.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PeopleTestDataProviderUtils {

    public static String supplyValidContent() {
        return """
                person_ID,name,first,last,middle,email,phone,title
                12,", Rosella ",Rosella,Burks,,BurksR@univ.edu,9635551253,Professor
                13,"Avila, Damien ",Damien,Avila,,AvilaD@univ.edu,9635551352,Professor
                14,"Olsen, Robin ",Robin,Olsen,,OlsenR@univ.edu,9635551378,Assistant Professor
                15,"Moises, Edgar Estes",Edgar,Moises,Estes,MoisesE@univ.edu,9635552731,Professor
                16,"Brian, Heath Pruitt",Heath,Brian,Pruitt,BrianH@univ.edu,9635552800,Associate Curator
                17,"Mosley, Edmund ",Edmund,Mosley,,MosleyE@univ.edu,9635552945,Assistant Professor
                18,"Derek, Antoine Mccoy",Antoine,Derek,Mccoy,DerekA@univ.edu,9635552992,Curator
                19,"Hawkins, Callie ",Callie,Hawkins,,HawkinsC@univ.edu,9635553350,Professor""";
    }
}
