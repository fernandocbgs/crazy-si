package ambiente;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ambiente.EnviarMsgTCP.TipoEnvio;
import DadosRobos.DadosRobos;
import Matematica.CalculoVetores;
import tcp.TCPClient;
import tcp.interfaces.IJason;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;

public class EnvSalvarRefem extends Environment implements IJason {
	private List<Integer> _portaRobos;
	private String _ip;
	private static int _numeroRound = 0;

	
	public static final String agSave = "agRobotSaver";
	public static final String agRefem = "agRefem";
//	public static final String agInimigo = "agInimigo";
	
	public static final int Width = 800;
	public static final int Height = 600;
	static double xF = 750.0; static double yF = 550.0; //minha area
	
    static Logger logger = Logger.getLogger(EnvSalvarRefem.class.getName());
    private ModelSalvarRefem model;
    private static DadosRobos r1;
	private static DadosRobos r2;
    
    @Override public void init(String[] args) {
    	//iniciaServidorTCP();
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
    	//recupera os dados dos robos via TCP
    	atualizarDadosRobosViaTCP();
    }
    
    private void enviarOrdem(List<String> ordens, int indice){
    	EnviarMsgTCP en = new EnviarMsgTCP(TipoEnvio.enviarOrdens, _portaRobos.get(indice), _ip);
    	en.setOrdens(ordens);
    	en.start();
    }
    
    private DadosRobos getDados(int indice) {
    	EnviarMsgTCP en = new EnviarMsgTCP(TipoEnvio.pedirDados, _portaRobos.get(indice), _ip);
    	en.start();
    	if (!en.isTerminou()) { aguardar(20);}
    	return en.getDados();
    }
    
    private void aguardar(long tempo){
    	try { Thread.sleep(tempo); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    //-------------------------------
    @Override public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);

        atualizarDadosRobosViaTCP();
        if (r1 == null || r2 == null) {return false;}
        
        //action.getFunctor().equals
        //System.err.println("action: " + action);
        
        try {
        	if (action.equals(Literal.parseLiteral("aproximar")) ){
            	model.AproximarRefem();
        	}else if (action.equals(Literal.parseLiteral("ludibriar"))){
            	model.LudibriarInimigo();
        	} else if (action.equals(Literal.parseLiteral("seguirRbSalvador"))) {
        		model.SeguirRoboSalvador();
        	} else if (action.equals(Literal.parseLiteral("voltarMA"))) {
        		model.VoltarMinhaArea();
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
        aguardar(200);
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
    	//recupera os dados dos robos via TCP
    	r1 = getDados(0);
    	r2 = getDados(1);
//    	if (r1 == null) {
//    		r1 = getDados(0);
//    	} else {
//    		if (r1.isExecutandoAlgo()) { aguardar(20); } else {
//    			r1 = getDados(0);
//    		}
//    	}
//    	if (r2 == null) {
//    		r2 = getDados(1);
//    	} else {
//    		if (r2.isExecutandoAlgo()) { aguardar(20); } else {
//    			r2 = getDados(1);
//    		}
//    	}
        
        if (r1 == null || r2 == null) {return;}
        
        //System.out.println("#r1: " + r1.isExecutandoAlgo());
        //System.out.println("##r2: " + r2.isExecutandoAlgo());
        
        if (r1.getNumeroRound() > _numeroRound) {
        	_numeroRound = r1.getNumeroRound();
        	//remove a percepção que ele esta perto do refém
        	//seria a reinicilização do jason
        	removePercept(agSave,Literal.parseLiteral("pertoRefem."));
        	removePercept(agRefem, Literal.parseLiteral("refemSeguir."));
        	//problema: ao reiniciar o robocode, o jason pode não rechamar este método
        	//System.out.println("#inicio jason");
        }
        
    }
    
    
    class ModelSalvarRefem extends GridWorldModel {
        //boolean r1HasGarb = false; // whether r1 is carrying garbage or not

        private ModelSalvarRefem() {
            super(Width, Height, 2); //3º parametro = nº agentes
            
            if (r1 == null || r2 == null) {return;}
            
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
        	if (r1 == null || r2 == null) {return;}
        	
//        	Location agSaveL = getAgPos(0); //agent Save
//            Location agRefemL = getAgPos(1); //refem
            //Location agInimigoL = getAgPos(2); //inimigo
            
            if (pertoRefem()){
            	//adiciona a percepção que chegou perto do refem, então ele deve parar
            	//a caminhada em direção à ele
            	//addPercept(r1.getNomeRobo(), Literal.parseLiteral("pertoRefem."));
            	addPercept(agSave, Literal.parseLiteral("pertoRefem."));
            	addPercept(agRefem, Literal.parseLiteral("refemSeguir."));
            	return;
            }

            //agSaveL, agRefemL
            List<String> ordens = CalculosRoboCode.getOrdensSalvarRefem();
            if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
        }
        
        void SeguirRoboSalvador(){
        	//System.out.println("SeguirRoboSalvador");
        	atualizarDadosRobosViaTCP();
        	if (r1 == null || r2 == null) {return;}
        	
        	if (campoSeguroRefem()) {
        		removePercept(agRefem, Literal.parseLiteral("refemSeguir."));
        		addPercept(agRefem, Literal.parseLiteral("campoSeguro."));
        		return;
        	}
        	
        	List<String> ordens = CalculosRoboCode.getOrdensRefemSeguirRoboSalvador();
        	if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 1);
        } 
        
        void VoltarMinhaArea(){
        	atualizarDadosRobosViaTCP();
        	if (r1 == null || r2 == null) {return;}
        	
        	if (volteiMinhaArea()) { 
        		addPercept(agSave, Literal.parseLiteral("voltei.")); return; 
        	}
        	
        	List<String> ordens = CalculosRoboCode.getOrdensVoltarMinhaArea();
        	if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
        }
        
        private boolean pertoRefem() {
        	return CalculosRoboCode.getDistanciaRefem() <= 80;
        }
        private boolean volteiMinhaArea(){
        	return CalculoVetores.distanciaPontos(r1.getX(), r1.getY(),xF, yF) <= 20;
        }
        private boolean campoSeguroRefem(){
        	return r2.getX() >= 400 && r2.getY() >= 500;
        }
    }
    
    //-----------------------------------------------------------
    static class CalculosRoboCode {
    	public CalculosRoboCode(){}
    	
    	public static List<String> getOrdensSalvarRefem(){
    		List<String> ordens = new ArrayList<String>();
    		
    		//double anguloCalculo = CalculoVetores.getAngulo(r1, r2);
    		double qtdVirar = CalculoVetores.getQuantidadeVirar(r1, r2);
//    		System.out.println("anguloCalculo: " + anguloCalculo);
//    		System.out.println("heading: " + r1.getHeading());
//    		System.out.println("virar: " + qtdVirar);
    		
    		addOrdemVirar(ordens, qtdVirar);
    		
			ordens.add("5");
			double distancia = getDistanciaRefem();
			
			//System.out.println("distancia: " + distancia);
			if (distancia <= 50) {
				ordens.add(distancia + "");
			}  else {
				
				if (distancia > 100) {
					ordens.add((distancia-50.0) + "");
				} else {
					ordens.add(50 + "");
				}
			}
    		return ordens;
    	}
    	
    	public static List<String> getOrdensRefemSeguirRoboSalvador(){
    		List<String> ordens = new ArrayList<String>();
    		
    		//double anguloRoboRefem = CalculoVetores.getAngulo(r2, r1);
    		//double anguloPosicaoFinal = CalculoVetores.getAngulo(r1.getX(), r1.getY(), xF, yF);
    		//double dif = Math.abs(anguloRoboRefem-anguloPosicaoFinal);
    		
//    		System.out.println("anguloRoboRefem: " + anguloRoboRefem);
//    		System.out.println("anguloPosicaoFinal: " + anguloPosicaoFinal);
//    		System.out.println("dif: " + dif);
    		
    		double qtdVirar = CalculoVetores.getQuantidadeVirar(r2, r1);
    		
			
			double distancia = getDistanciaRefem();
			double vlrSeguir = 0.0;
			//System.out.println("distancia: " + distancia);
			if (distancia > 100) {
				vlrSeguir = distancia-50.0;
			} else {
				if (distancia > 60.0) {
					vlrSeguir = 40.0;
				} else {
					vlrSeguir = -30.0; //dá a ré
				}
			}
			if (Math.abs(vlrSeguir) > 0.0) {
				//if (vlrSeguir > 0)
				addOrdemVirar(ordens, qtdVirar);
				ordens.add("5");
				ordens.add(vlrSeguir + "");
			}
    		return ordens;
    	}
    	
    	public static List<String> getOrdensVoltarMinhaArea(){
    		List<String> ordens = new ArrayList<String>();
    		
    		//tenho que verificar se não vou bater no robo refém (ou outros robos)
    		//captura o angulo onde esta o robo refem 
    		double anguloRoboRefem = CalculoVetores.getAngulo(r1, r2);
    		double anguloPosicaoFinal = CalculoVetores.getAngulo(r1.getX(), r1.getY(), xF, yF);
    		double dif = anguloRoboRefem-anguloPosicaoFinal;
    		
    		double distancia = CalculoVetores.distanciaPontos(r1.getX(), r1.getY(), r2.getX(), r2.getY());
    		
    		//System.out.println("dif: " + dif);

			double qtdVirar = CalculoVetores.getQuantidadeVirar(r1.getX(), r1.getY(), xF, yF, r1.getHeading());
			
			//deve dar a ré se o robo estiver na sua frente
			//como ver que alguem esta no meu caminho ?
			//if (dif <= 50.0){
			if (distancia <= 50.0){
				System.out.println("#####dif: " + dif + ",qtdVirar: " + qtdVirar + 
								   ",r1.h: " + r1.getHeading() + ",dis: " + distancia);
			}
			
//			if (dif <= 50.0) {
//				addOrdemVirar(ordens, qtdVirar);
//				ordens.add("5");
//				ordens.add("" + (-30)); //ré
//			} else {
			
//			if (dif <= 50.0) {
//				qtdVirar = r1.getHeading() - anguloPosicaoFinal + (50.0);
//				//System.out.println("qtdVirar: " + qtdVirar );
//				
//				addOrdemVirar(ordens, qtdVirar);
//				
//				ordens.add("5");
//				ordens.add("" + 50.0);
//				
//			} else {
				addOrdemVirar(ordens, qtdVirar);
				ordens.add("5");
				ordens.add("" + CalculoVetores.distanciaPontos(r1.getX(), r1.getY(),xF, yF));
//			}
			
    		return ordens;
    	}
    	
    	public static double getDistanciaRefem() {
    		return CalculoVetores.distanciaPontos(r1.getX(), r1.getY(),r2.getX(), r2.getY());
    	}
    	
    	private static void addOrdemVirar(List<String> ordens, double qtdVirar){
    		if (
    				!Matematica.CalculoVetores.Entre(qtdVirar, 0, 1.0) && 
    				!Matematica.CalculoVetores.Entre(qtdVirar, 360, 1.0)
    			) {
    			ordens.add("3"); //virar esquerda
    			ordens.add(""+ qtdVirar);
    		}
    		
    	}
    	
    }

}

class EnviarMsgTCP extends Thread {
	public enum TipoEnvio {pedirDados, enviarOrdens}
	private TipoEnvio _tipo;
	private int _porta; private String _ip;
	private List<String> _ordens = null;
	private DadosRobos _dados = null;
	private boolean _terminou = false;
	
	public void setTerminou(boolean enviouDados) { this._terminou = enviouDados; }
	public boolean isTerminou() { return _terminou; }
	public void setOrdens(List<String> ordens) { this._ordens = ordens; }
	public List<String> getOrdens() { return _ordens; }
	public void setDados(DadosRobos dados) { this._dados = dados; }
	public DadosRobos getDados() { return _dados; }
	
	public EnviarMsgTCP(TipoEnvio tp, int porta, String ip){
		_tipo = tp;
		_porta = porta;
		_ip = ip;
		setTerminou(false);
	}
	
	public void run(){
		setDados(null); setTerminou(false);
		switch(_tipo){
			case enviarOrdens: EnviarOrdens(); break;
			case pedirDados: pedirDados(); break;
		}
	}
	
	private void pedirDados(){
		TCPClient cli = new TCPClient(_porta, _ip);
		setDados(cli.pedirDados());
		cli = null;
		setTerminou(true);
	}
	private void EnviarOrdens(){
		TCPClient cli = new TCPClient(_porta, _ip);
		cli.enviarOrdem(getOrdens());
		cli = null;
		setTerminou(true);
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
//private boolean _esperar = false;
//private TCPServer _server = null;
//private int _portaServidorTCPJason = 7770;

//while (_esperar) { //espera, até que o robo termine a sua execução
//System.out.println("### JASON ESTOU ESPERANDO....");
//aguardar(600);
//}

//public void Continuar(DadosRobos dados) {
//System.out.println("### DEVO CONTINUAR");
//_esperar = false;
//}

//if (en.isTerminou()) { //enviou uma ordem
//_esperar = true;
//}

//private void iniciaServidorTCP(){
//if (_server == null) {
//	_server = new TCPServer(_portaServidorTCPJason);
//	_server.setIJason(this);
//	_server.start();
//}
//}

//private void killServidorTCP(){
//_server.parar(); //_server.stop();
//_server = null;
//}