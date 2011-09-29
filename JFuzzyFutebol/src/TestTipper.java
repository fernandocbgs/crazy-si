import net.sourceforge.jFuzzyLogic.FIS;
//import net.sourceforge.jFuzzyLogic.rule.Rule;
//import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

/**
 * Test parsing an FCL file
 * @author Emerson / Lucas
 * http://jfuzzylogic.sourceforge.net/html/index.html
 */
public class TestTipper {
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

        // Set inputs
        fis.setVariable("posicao", 4);
        fis.setVariable("adversario", 3);
       
        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        fis.getVariable("forca").chartDefuzzifier(true);

        // Print ruleSet
        System.out.println(fis);
    }
}