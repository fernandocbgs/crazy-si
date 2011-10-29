package ambiente;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Logger;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

public class EnvSalvarRefem extends Environment {
	public static final String agSave = "agRobotSaver";
	public static final String agRefem = "agRefem";
	public static final String agInimigo = "agInimigo";
	
	public static final int GSize = 10; // grid size
    static Logger logger = Logger.getLogger(EnvSalvarRefem.class.getName());
    private ModelSalvarRefem model; private ViewSalvarRefem view;
    
    
    @Override
    public void init(String[] args) {
        model = new ModelSalvarRefem();
        view = new ViewSalvarRefem(model);
        model.setView(view);
        updatePercepts();
    }
    
    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        
        //action.getFunctor().equals
        //System.err.println("action: " + action);
        
        try {
        	if (action.equals(Literal.parseLiteral("aproximar"))){
            	model.AproximarRefem();
        	}else if (action.equals(Literal.parseLiteral("ludibriar"))){	
            	model.LudibriarInimigo();
//            } else if (action.getFunctor().equals(move_towards)) {
//                //int x = (int)((NumberTerm)action.getTerm(0)).solve();
//                //int y = (int)((NumberTerm)action.getTerm(1)).solve();
//                model.moveTowards();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updatePercepts();

        //para nós humanos
        try {Thread.sleep(200);} catch (Exception e) {}
        return true;
    }
    
    /** creates the agents perception based on the ModelSalvarRefem */
    void updatePercepts() {
        clearPercepts();
        
        Location ag_save = model.getAgPos(0);
        Location ag_refem = model.getAgPos(1);
        
        Literal pos1 = Literal.parseLiteral("pos("+agSave+"," + ag_save.x + "," + ag_save.y + ")");
        Literal pos2 = Literal.parseLiteral("pos("+agRefem+"," + ag_refem.x + "," + ag_refem.y + ")");

        addPercept(pos1);
        addPercept(pos2);
        
//        if (model.hasObject(GARB, r1Loc)) {
//            addPercept(g1);
//        }
//        if (model.hasObject(GARB, r2Loc)) {
//            addPercept(g2);
//        }
    }
    
    
    class ModelSalvarRefem extends GridWorldModel {
        //boolean r1HasGarb = false; // whether r1 is carrying garbage or not

        private ModelSalvarRefem() {
            super(GSize, GSize, 3); //3, agentes
            
            // initial location of agents
            try {
            	//tem que ser -1, se não estoura o tamanho do Desenho
                setAgPos(0, GSize-1, GSize-1); //agente que deve salvar
                //setAgPos(1, new java.util.Random().nextInt(GSize),new java.util.Random().nextInt(GSize)); //agente refem
                setAgPos(1, 0,0); //agente refem
                setAgPos(2, 2,2);//agInimigo
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // initial location of garbage
//            add(GARB, 3, 0);
//            add(GARB, GSize-1, 0);
//            add(GARB, 1, 2);
//            add(GARB, 0, GSize-2);
//            add(GARB, GSize-1, GSize-1);
        }
        
        
        void LudibriarInimigo() throws Exception {
        	Location agSaveL = getAgPos(0); //agent Save
            Location agRefemL = getAgPos(1); //refem
            Location agInimigoL = getAgPos(2); //inimigo
            
            //tenta dar a volta no inimigo
            //se a posição do inimigo esta próxima do topo, tenta ir por baixo
            //int xI = agInimigoL.x; 
            int yI = agInimigoL.y;
            
            if (yI <= 2) { //por baixo
            	agSaveL.x--;
            } else { //por cima
            	agSaveL.y--;
            }
            
            if (!pertoInimigo(0)) {
            	//removePercept(agInimigo, Literal.parseLiteral("agenteProximo.")); //informa ao inimigo
            	removePercept(agSave, Literal.parseLiteral("pertoInimigo.")); //remove a percepção
            }
            
            setAgPos(0, agSaveL);
        }
        
        void AproximarRefem() throws Exception {
        	Location agSaveL = getAgPos(0); //agent Save
            Location agRefemL = getAgPos(1); //refem
            Location agInimigoL = getAgPos(2); //inimigo
            
            if (pertoRefem()){
            	//adiciona a percepção que chegou perto do refem, então ele deve parar
            	//a caminhada em direção à ele
            	addPercept(agSave, Literal.parseLiteral("pertoRefem."));
            	return;
            }
            
            //verifica se não esta atropelando o inimigo
            if (pertoInimigo(1)) {
            	
            	//send(agInimigo,achieve,write(book)); // sent by Bob
            	
            	//addPercept(agInimigo, Literal.parseLiteral("agenteProximo.")); //informa ao inimigo que esta proximo
            	//perto inimigo
            	addPercept(agSave, Literal.parseLiteral("pertoInimigo."));
            	return;
            }
            
            if (agSaveL.x != agRefemL.x) {
	            if (agSaveL.x < agRefemL.x && agSaveL.x < getWidth()) {
	            	agSaveL.x++;
	            } else if (agSaveL.x > 0) {
	            	agSaveL.x--;
	            }
            }
            if (agSaveL.y != agRefemL.y) {
	            if (agSaveL.y < agRefemL.y && agSaveL.y < getHeight()) {
	            	agSaveL.y++;
	            } else if (agSaveL.y > 0) {
	            	agSaveL.y--;
	            }
            }
            if (agSaveL.x == getWidth()) { agSaveL.x--; }
            if (agSaveL.y == getHeight()) { agSaveL.y--; }
            
            
            //System.out.println("["+agSaveL.x + "," + agSaveL.y + "]");
            
            setAgPos(0, agSaveL);
            //setAgPos(1, agRefemL);
            //setAgPos(2, agInimigoL);
        }
        
        private boolean pertoRefem() {
        	Location agSaveL = getAgPos(0); //agent Save
            Location agRefemL = getAgPos(1); //refem
        	return ((agSaveL.x == agRefemL.x+1 || agSaveL.x == agRefemL.x) 
            		&& (agSaveL.y == agRefemL.y+1 || agSaveL.y == agRefemL.y));
        }
        
        /**
         * @maxDistance a distância máxima a ser levada em consideração
         * */
        private boolean pertoInimigo(int maxDistance) {
        	Location agSaveL = getAgPos(0); //agent Save
            Location agInimigoL = getAgPos(2); //inimigo
        	return ((agSaveL.x == agInimigoL.x || agSaveL.x == agInimigoL.x+maxDistance) 
            		&& (agSaveL.y == agInimigoL.y || agSaveL.y == agInimigoL.y+maxDistance));
        }
        
        
        //------------------------ não usado
        
        void moveTowards() throws Exception {
            Location agSave = getAgPos(0);
            Location agRefem = getAgPos(1);
            
            if (agSave.x < agRefem.x) {
            	agSave.x++;
            } else {
            	agSave.x--;
            }
            if (agSave.y < agRefem.y) {
            	agSave.y++;
            } else {
            	agSave.y--;
            }
            
            if (agSave.x == getWidth()) { agSave.x = 0; }
            //if (agSave.y == getHeight()) { return; }

            // finished searching the whole grid
            
            setAgPos(0, agSave);
            
            //desenho agente r2
            //setAgPos(1, getAgPos(1)); // just to draw it in the view
        }
        
//        void moveTowards(int x, int y) throws Exception {
//            Location r1 = getAgPos(0);
//            if (r1.x < x)
//                r1.x++;
//            else if (r1.x > x)
//                r1.x--;
//            if (r1.y < y)
//                r1.y++;
//            else if (r1.y > y)
//                r1.y--;
//            setAgPos(0, r1);
//            
//
//            //posicao do agente r2
//            //setAgPos(1, getAgPos(1)); // just to draw it in the view
//        }
        
//        void pickGarb() {
//            // r1 location has garbage
////            if (model.hasObject(GARB, getAgPos(0))) {
////                // sometimes the "picking" action doesn't work
////                // but never more than MErr times
////                if (random.nextBoolean() || nerr == MErr) {
////                    remove(GARB, getAgPos(0));
////                    nerr = 0;
////                    r1HasGarb = true;
////                } else {
////                    nerr++;
////                }
////            }
//        }
//        void dropGarb() {
//            if (r1HasGarb) {
//                r1HasGarb = false;
//                //add(GARB, getAgPos(0));
//            }
//        }
//        void burnGarb() {
//            // r2 location has garbage
//            if (model.hasObject(GARB, getAgPos(1))) {
//                remove(GARB, getAgPos(1));
//            }
//        }
    }
    
    //-----------------------------------------------------------
    class ViewSalvarRefem extends GridWorldView {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ViewSalvarRefem(ModelSalvarRefem model) {
            super(model, "Salvar Refem", 600);
            defaultFont = new Font("Arial", Font.BOLD, 10); // change default font
            setVisible(true);
            repaint();
        }

        /** draw application objects */
        @Override
        public void draw(Graphics g, int x, int y, int object) {
            switch (object) {
                //case MarsEnv.GARB: drawGarb(g, x, y);  break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
            String label = "R"+(id+1);
            c = Color.blue;
            
            if (id == 0) {
                c = Color.yellow;
//                if (((MarsModel)model).r1HasGarb) {
//                    label += " - G";
//                    c = Color.orange;
//                }
            }
            super.drawAgent(g, x, y, c, id-1);
            if (id == 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.white);
            }
            super.drawString(g, x, y, defaultFont, label);
        }

//        public void drawGarb(Graphics g, int x, int y) {
//            super.drawObstacle(g, x, y);
//            g.setColor(Color.white);
//            drawString(g, x, y, defaultFont, "G");
//        }

    }
    
}
