package ambiente;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import DadosRobos.DadosRobos;
import Matematica.CalculoVetores;
import tcp.TCPClient;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;

public class EnvSalvarRefem extends Environment {
	private List<Integer> _portaRobos;
	private String _ip;
	private TCPClient[] _tcpcli;
	private int _numeroRound = 0;
	
	public static final String agSave = "agRobotSaver";
//	public static final String agRefem = "agRefem";
//	public static final String agInimigo = "agInimigo";
	
	public static final int Width = 800;
	public static final int Height = 600;
    static Logger logger = Logger.getLogger(EnvSalvarRefem.class.getName());
    private ModelSalvarRefem model;
    private DadosRobos r1, r2;
    
    @Override public void init(String[] args) {
    	configuracoes();
        model = new ModelSalvarRefem();
        updatePercepts();
    }
    
    /**
     * seta as configurações dos clientes tcp
     * */
    private void configuracoes(){
    	_portaRobos = new ArrayList<Integer>();
    	_portaRobos.add(7891);
    	_portaRobos.add(7892);
    	_ip = "localhost";
    	
    	_tcpcli = new TCPClient[_portaRobos.size()];
    	for (int i = 0; i < _tcpcli.length; i++) _tcpcli[i] = new TCPClient(_portaRobos.get(i), _ip);
		
    	//recupera os dados dos robos via TCP
    	r1 = getDadosR(0); 
        r2 = getDadosR(1);
    }
    
    private TCPClient getTCPClient(int indice){
    	return _tcpcli[indice];
    }
    
    private DadosRobos getDadosR(int indice){
    	return new DadosRobos(getDados(indice));
    }
    private List<String> getDados(int indice) {
    	return getTCPClient(indice).pedirDados();
    }
    private void enviarOrdem(List<String> ordens, int indice) {
    	getTCPClient(indice).enviarOrdem(ordens);
    }
    
    
    @Override public boolean executeAction(String ag, Structure action) {
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
        
        //Location ag_save = model.getAgPos(0);
        //Location ag_refem = model.getAgPos(1);
        
        atualizarDadosRobosViaTCP(); //recupera os dados dos robos via TCP
        
        if (r1 == null || r2 == null) {return;}
        
        Literal pos1 = Literal.parseLiteral("pos("+agSave+"," + r1.getX() + "," + r1.getY() + ")");
        Literal pos2 = Literal.parseLiteral("pos("+agSave+"," + r2.getX() + "," + r2.getX() + ")");
        
        addPercept(pos1);
        addPercept(pos2);
    }
    
    private void atualizarDadosRobosViaTCP(){
        r1 = getDadosR(0); //recupera os dados dos robos via TCP
        r2 = getDadosR(1);
        
        if (r1.getNumeroRound() > _numeroRound) {
        	_numeroRound = r1.getNumeroRound();
        	//remove a percepção que ele esta perto do refém
        	//seria a reinicilização do jason
        	removePercept(agSave,Literal.parseLiteral("pertoRefem."));
        	//problema: ao reiniciar o robocode, o jason pode não rechamar este método
        	//System.out.println("#inicio jason");
        }
        
    }
    
    
    class ModelSalvarRefem extends GridWorldModel {
        //boolean r1HasGarb = false; // whether r1 is carrying garbage or not

        private ModelSalvarRefem() {
            super(Width, Height, 2); //3º parametro = nº agentes

            // initial location of agents
            try {
                setAgPos(0, (int)r1.getX(), (int)r1.getY()); //agente que deve salvar
                setAgPos(1, (int)r2.getX(), (int)r2.getY()); //agente refem
                //setAgPos(2, (int)r3.getX(), (int)r3.getY()); //agente Inimigo
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void LudibriarInimigo() throws Exception {
        	
        	//não faz nada por enquanto
        	
//        	Location agSaveL = getAgPos(0); //agent Save
//            Location agRefemL = getAgPos(1); //refem
//            Location agInimigoL = getAgPos(2); //inimigo
//            
//            //tenta dar a volta no inimigo
//            //se a posição do inimigo esta próxima do topo, tenta ir por baixo
//            //int xI = agInimigoL.x; 
//            int yI = agInimigoL.y;
//            
//            if (yI <= 2) { //por baixo
//            	agSaveL.x--;
//            } else { //por cima
//            	agSaveL.y--;
//            }
//            
//            if (!pertoInimigo(0)) {
//            	//removePercept(agInimigo, Literal.parseLiteral("agenteProximo.")); //informa ao inimigo
//            	removePercept(_r1.getNomeRobo(), Literal.parseLiteral("pertoInimigo.")); //remove a percepção
//            }
//            
//            setAgPos(0, agSaveL);
        }
        
        void AproximarRefem() throws Exception {
        	//atualiza r1 e r2
        	atualizarDadosRobosViaTCP();
        	
//        	Location agSaveL = getAgPos(0); //agent Save
//            Location agRefemL = getAgPos(1); //refem
            //Location agInimigoL = getAgPos(2); //inimigo
            
            if (pertoRefem()){
            	//adiciona a percepção que chegou perto do refem, então ele deve parar
            	//a caminhada em direção à ele
            	//addPercept(r1.getNomeRobo(), Literal.parseLiteral("pertoRefem."));
            	addPercept(agSave,Literal.parseLiteral("pertoRefem."));
            	return;
            }

            //backup
//            //verifica se não esta atropelando o inimigo
//            if (pertoInimigo(1)) {
//            	
//            	//send(agInimigo,achieve,write(book)); // sent by Bob
//            	
//            	//addPercept(agInimigo, Literal.parseLiteral("agenteProximo.")); //informa ao inimigo que esta proximo
//            	//perto inimigo
//            	addPercept(_r1.getNomeRobo(), Literal.parseLiteral("pertoInimigo."));
//            	return;
//            }

//            if (agSaveL.x != agRefemL.x) {
//	            if (agSaveL.x < agRefemL.x && agSaveL.x < getWidth()) {
//	            	agSaveL.x++;
//	            } else if (agSaveL.x > 0) {
//	            	agSaveL.x--;
//	            }
//            }
//            if (agSaveL.y != agRefemL.y) {
//	            if (agSaveL.y < agRefemL.y && agSaveL.y < getHeight()) {
//	            	agSaveL.y++;
//	            } else if (agSaveL.y > 0) {
//	            	agSaveL.y--;
//	            }
//            }
//            if (agSaveL.x == getWidth()) { agSaveL.x--; }
//            if (agSaveL.y == getHeight()) { agSaveL.y--; }
//            
//            
//            //System.out.println("["+agSaveL.x + "," + agSaveL.y + "]");
//            
//            setAgPos(0, agSaveL);
//            //setAgPos(1, agRefemL);
//            //setAgPos(2, agInimigoL);
//            
//            List<String> ordens = new ArrayList<String>();
//            ordens.add("5"); 
//            ordens.add(agSaveL.x + "");
            
            //agSaveL, agRefemL
            List<String> ordems = new CalculosRoboCode().getOrdems();
            if (ordems != null)
            	enviarOrdem(ordems, 0);
            
        }
        
        private boolean pertoRefem() {
        	atualizarDadosRobosViaTCP();
        	double dis = Matematica.CalculoVetores.distanciaPontos(r1.getX(), r1.getY(),r2.getX(), r2.getY());
        	return dis <= 80;
        }

    }
    
    //-----------------------------------------------------------
    class CalculosRoboCode {
    	public CalculosRoboCode(){}
    	
    	public List<String> getOrdems(){
    		List<String> ordens = new ArrayList<String>();
    		
			ordens.add("3"); //virar esquerda
			ordens.add(""+ getAngulo());
			
			ordens.add("5");
			double distancia = getDistanciaRefem();
			System.out.println("distancia: " + distancia);
			if (distancia <= 50) {
				ordens.add(distancia + "");
			}  else {
				ordens.add(50 + "");
			}
			
    		return ordens;
    	}
    	
    	public double getDistanciaRefem() {
    		atualizarDadosRobosViaTCP();
    		return Matematica.CalculoVetores.distanciaPontos(r1.getX(), r1.getY(),r2.getX(), r2.getY());
    	}
    	
    	private double getAngulo(){
    		double rt= 0.0;
    		rt = CalculoVetores.getAnguloDoisPontos(
    				r1.getX(), r1.getY(),r2.getX(), r2.getY(),
    				r1.getHeading()
    		 	);
    		return rt;
    	}
    	
    }
    
}

/**
 * 0 - parar
 * 1 - reiniciar robo
 * 2 - atirar
 * 3 - virar esquerda
 * 4 - virar direita
 * 5 - andar
 **/