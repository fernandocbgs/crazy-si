package ambiente;

import interfaces.IMetodosJason;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import robocode.ModelSalvarRefem;
import robocode.DadosRobos.DadosRobos;
import tcp.TCPServer;
import tcp.enviarmsg.EnviarMsgTCP;
import tcp.enviarmsg.EnviarMsgTCP.TipoEnvio;
import tcp.interfaces.IJason;
import tcp.pacotes.AnalisePacotes;
import tcp.pacotes.CriadorPacotes;
import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

public class EnvSalvarRefem extends Environment implements IJason, IMetodosJason {
	private List<Integer> _portaRobos;
	private String _ip;
	private int _numeroRound = 0;

	private boolean _esperar = false;
	private TCPServer _server = null;
	private int _portaServidorTCPJason = 7770;
	
	public final String agSave = "agRobotSaver";
	public final String agRefem = "agRefem";
//	public final String agInimigo = "agInimigo";
	
	public final int Width = 800;
	public final int Height = 600;
	private double xF = 750.0; private double yF = 550.0; //minha area
	
    private static Logger logger = Logger.getLogger(EnvSalvarRefem.class.getName());
    private ModelSalvarRefem model;
    private DadosRobos r1 = null;
	private DadosRobos r2 = null;
	
	public DadosRobos getR1() { return r1; }
	public DadosRobos getR2() { return r2; }
	
	public String getAgSave() { return agSave; }
	public String getAgRefem() { return agRefem; }
	public double getXF() { return xF; }
	public double getYF() { return yF; }
	public int getPorta(int indice) { return _portaRobos.get(indice); }
	public String getIp() { return _ip; }
	
    @Override public void init(String[] args) {
    	iniciaServidorTCP();
    	configuracoes();
        model = new ModelSalvarRefem(Width, Height, this, this);
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
    
	public void atualizarDadosRobos(DadosRobos r, int indice) {
		if (indice == 0) {
			r1 = r;
		} else if (indice==1) {
			r2 = r;
		}
	}
    
    private void getDados(int indice) {
    	EnviarMsgTCP en = new EnviarMsgTCP(TipoEnvio.pedirDados, _portaRobos.get(indice), _ip, this, indice);
    	en.start();
    }
    
	private void aguardar(long tempo){
    	try { Thread.sleep(tempo); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    //-------------------------------
    @Override public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);

        atualizarDadosRobosViaTCP();
        if (r1 == null || r2 == null) {return false;}
        
		while (_esperar) { // espera, até que o robo termine a sua execução
			//System.out.println("### JASON ESTOU ESPERANDO....");
			aguardar(600);
		}
		
        //action.getFunctor().equals
		
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
            	//se nenhuma ação, significa que terminou os planos
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updatePercepts();
        
        _esperar = true;
        
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
    
    public void atualizarDadosRobosViaTCP(){
    	//recupera os dados dos robos via TCP
    	getDados(0); getDados(1);
        
        if (r1 == null || r2 == null) {return;}
        
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
    
	public void Continuar(DadosRobos dados) {
		//System.out.println("### DEVO CONTINUAR");
		_esperar = false;
		atualizarDadosRobosViaTCP();
	}

	private void iniciaServidorTCP() {
		if (_server == null) {
			_server = new TCPServer(_portaServidorTCPJason);
			_server.setIJason(this);
			_server.start();
		}
	}

	@SuppressWarnings("unused")
	private void killServidorTCP() {
		_server.parar(); // _server.stop();
		_server = null;
	}
    
    /**
     *analise de pacotes TCP 
     * */
	public void analisePacote(byte[] pacote, DataOutputStream out) throws IOException {
		CriadorPacotes cp = new CriadorPacotes();
		switch (AnalisePacotes.getTipo(pacote)) {
			case avisarJasonToJason:
				//System.out.println("RECEBI UM PACOTE - JASON");
				//jason recebu um pacote do tipo 'continue a sua execução'
				this.Continuar(AnalisePacotes.getDadosRobo(pacote)); //recebe os dados do robô
				//System.out.println("JASON - RESPOSTA via OUT - Cliente ROBOT TCP");
				out.write(cp.pacoteRespostaJason()); //envia uma resposta ao cliente robô
				break;
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