package Utils;

public class TeamsEnum {
    public  enum countries{England, France, Spain, Germany, Portugal, Italy, Austria, belgium, croatia, }
    public enum englandTeams {Arsenal, ManCity, Chelsea,Liverpool, Tottenham, WestHam,ManUnited}
    public enum franceTeams{Psg("Paris SG"), Lyon;
String ps;
        franceTeams(String ps) {
            this.ps = ps;
        }

        franceTeams() {

        }
    }
    public enum spainTeams{Barcelona, RealMadrid, AtleticoMadrid, Sevilla}
}
