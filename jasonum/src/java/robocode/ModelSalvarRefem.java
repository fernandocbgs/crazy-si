package robocode;

import interfaces.IMetodosJason;
import jason.asSyntax.Literal;
import jason.environment.grid.GridWorldModel;
import java.util.List;
import ambiente.EnvSalvarRefem;
import tcp.enviarmsg.EnviarMsgTCP;
import tcp.enviarmsg.EnviarMsgTCP.TipoEnvio;
import Matematica.CalculoVetores;

public class ModelSalvarRefem extends GridWorldModel {
	private IMetodosJason _imj;
	private EnvSalvarRefem _esr;
	//boolean r1HasGarb = false; // whether r1 is carrying garbage or not

    public ModelSalvarRefem(int Width, int Height, IMetodosJason imj, EnvSalvarRefem esr) {
        super(Width, Height, 2); //3º parametro = nº agentes
        _imj = imj;
        _esr = esr;
        _imj.atualizarDadosRobosViaTCP();
       
        // initial location of agents
//        try {
//        	setAgPos(0, (int)_imj.getR1().getX(), (int)_imj.getR1().getY()); //agente que deve salvar
//            setAgPos(1, (int)_imj.getR2().getX(), (int)_imj.getR2().getY()); //agente refem
//            //setAgPos(2, (int)r3.getX(), (int)r3.getY()); //agente Inimigo
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void LudibriarInimigo() throws Exception {
    	
    	//não faz nada por enquanto
    	
//    	Location agSaveL = getAgPos(0); //agent Save
//        Location agRefemL = getAgPos(1); //refem
//        Location agInimigoL = getAgPos(2); //inimigo
//        
//        //tenta dar a volta no inimigo
//        //se a posição do inimigo esta próxima do topo, tenta ir por baixo
//        //int xI = agInimigoL.x; 
//        int yI = agInimigoL.y;
//        
//        if (yI <= 2) { //por baixo
//        	agSaveL.x--;
//        } else { //por cima
//        	agSaveL.y--;
//        }
//        
//        if (!pertoInimigo(0)) {
//        	//removePercept(agInimigo, Literal.parseLiteral("agenteProximo.")); //informa ao inimigo
//        	removePercept(_r1.getNomeRobo(), Literal.parseLiteral("pertoInimigo.")); //remove a percepção
//        }
//        
//        setAgPos(0, agSaveL);
    }
    
    public void AproximarRefem() throws Exception {
    	//atualiza r1 e r2
    	_imj.atualizarDadosRobosViaTCP();
    	if (_imj.getR1() == null || _imj.getR2() == null) {return;}
    	
//    	Location agSaveL = getAgPos(0); //agent Save
//        Location agRefemL = getAgPos(1); //refem
        //Location agInimigoL = getAgPos(2); //inimigo
        
        if (pertoRefem()){
        	//adiciona a percepção que chegou perto do refem, então ele deve parar
        	//a caminhada em direção à ele
        	//addPercept(r1.getNomeRobo(), Literal.parseLiteral("pertoRefem."));
        	_esr.addPercept(_imj.getAgSave(), Literal.parseLiteral("pertoRefem."));
        	_esr.addPercept(_imj.getAgRefem(), Literal.parseLiteral("refemSeguir."));
        	return;
        }

        //agSaveL, agRefemL
        List<String> ordens = new CalculosRoboCode(_imj).getOrdensSalvarRefem();
        if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
    }
    
    public void SeguirRoboSalvador(){
    	//System.out.println("SeguirRoboSalvador");
    	_imj.atualizarDadosRobosViaTCP();
    	if (_imj.getR1() == null || _imj.getR2() == null) {return;}

    	if (campoSeguroRefem()) {
    		_esr.removePercept(_imj.getAgRefem(), Literal.parseLiteral("refemSeguir."));
    		_esr.addPercept(_imj.getAgRefem(), Literal.parseLiteral("campoSeguro."));
    		return;
    	}
    	
    	List<String> ordens = new CalculosRoboCode(_imj).getOrdensRefemSeguirRoboSalvador();
    	if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 1);
    } 
    
    public void VoltarMinhaArea(){
    	_imj.atualizarDadosRobosViaTCP();
    	if (_imj.getR1() == null || _imj.getR2() == null) {return;}
    	
    	if (volteiMinhaArea()) { 
    		_esr.addPercept(_imj.getAgSave(), Literal.parseLiteral("voltei.")); return; 
    	}
    	
    	List<String> ordens = new CalculosRoboCode(_imj).getOrdensVoltarMinhaArea();
    	if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
    }
    
    private boolean pertoRefem() {
    	return new CalculosRoboCode(_imj).getDistanciaRefem() <= 80;
    }
    private boolean volteiMinhaArea(){
    	return CalculoVetores.distanciaPontos(
    			_imj.getR1().getX(), _imj.getR1().getY(),_imj.getXF(), _imj.getYF()) <= 20;
    }
    private boolean campoSeguroRefem(){
    	return _imj.getR2().getX() >= 400 && _imj.getR2().getY() >= 500;
    }
    
    private void enviarOrdem(List<String> ordens, int indice){
    	EnviarMsgTCP en = new EnviarMsgTCP(TipoEnvio.enviarOrdens, _imj.getPorta(indice), _imj.getIp(), _imj, indice);
    	en.setOrdens(ordens);
    	en.start();
    }
    
}
