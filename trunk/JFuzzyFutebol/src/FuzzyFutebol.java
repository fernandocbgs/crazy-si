import net.sourceforge.jFuzzyLogic.FIS;
//import net.sourceforge.jFuzzyLogic.rule.Rule;
//import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

/**
 * Test parsing an FCL file
 * @author Emerson / Lucas
 * http://jfuzzylogic.sourceforge.net/html/index.html
 */
public class FuzzyFutebol {
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String fileName = "fcl/futebol.fcl";
        FIS fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '"
                                   + fileName + "'");
            return;
        }

        // Show
        fis.chart();

        /*
         posições do campo:
         	0 - 33: defesa (33 - 38 --> defesa / meio)
         	38 - 66: meio (33 - 38 --> meio / defesa, 66 - 75 --> meio / ataque)
    	    75 - 100: ataque (66 - 75 --> ataque / meio)
    	 força [0-10]
    	    0 - 3.3 --> fraca
    	    3.3 - 6.6 --> media
    	    6.6 - 10 --> forte
    	 adversário (distância em metros)
			0 - 20 -->: perto (20 - 32 --> perto/meio_perto)
			32 - 50 --> meio_perto (20 - 32 --> meio_perto/perto, e 50 - 63 --> meio_perto/longe)
    		63 - 100 --> longe (50 - 63 - longe / meio_perto) 
         * */
        // Set inputs
        fis.setVariable("posicao", 90); //posição no campo do jogador com a bola
        fis.setVariable("adversario", 2); //distancia do jogador com a bola (em metros) e não posição no campo !!!!!
        fis.setVariable("colega1", 40); //posição no campo do colega1
        fis.setVariable("colega2", 100); //posição no campo do colega2
       
        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        fis.getVariable("forca").chartDefuzzifier(true);

        // Print ruleSet
        System.out.println(fis);
    }
}