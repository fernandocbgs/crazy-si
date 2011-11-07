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
	private boolean _addPercepcaoPertoInimigo = false;
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
    	percepcoesPertoInimigo();
        List<String> ordens = new CalculosRoboCode(_imj).getOrdensLudibriarInimigo();
        if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
    }
    
    public void AproximarRefem() throws Exception {
    	percepcoesPertoInimigo();
        List<String> ordens = new CalculosRoboCode(_imj).getOrdensSalvarRefem();
        if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
    }
    
    private void percepcoesPertoInimigo(){
    	_imj.atualizarDadosRobosViaTCP();
    	if (_imj.getR1() == null || _imj.getR2() == null) {return;}
    	
//    	Location agSaveL = getAgPos(0); //agent Save
//        Location agRefemL = getAgPos(1); //refem
        //Location agInimigoL = getAgPos(2); //inimigo
    	if (pertoInimigo() && !_addPercepcaoPertoInimigo) {
    		_addPercepcaoPertoInimigo = true;
    		_esr.addPercept(_imj.getAgInimigo(), Literal.parseLiteral("bloquear."));
    		_esr.addPercept(_imj.getAgSave(), Literal.parseLiteral("pertoInimigo."));
    	} else {
    		//por enquanto não vou remover a percepção aqui
    		//if (!pertoInimigo()) _esr.removePercept(_imj.getAgSave(), Literal.parseLiteral("pertoInimigo."));
    	}
        if (pertoRefem()){
        	//adiciona a percepção que chegou perto do refem, então ele deve parar
        	//a caminhada em direção à ele
        	//addPercept(r1.getNomeRobo(), Literal.parseLiteral("pertoRefem."));
        	/*if (!pi)*/ 
        	_esr.addPercept(_imj.getAgSave(), Literal.parseLiteral("pertoRefem."));
        	//_esr.Continuar(null); //jason continuar
        	_esr.addPercept(_imj.getAgRefem(), Literal.parseLiteral("refemSeguir."));
        	
        	//aguarda um pouco, esperando que o Jason entenda a percepção
        	//try { Thread.sleep(100); } catch (Exception e) {	e.printStackTrace(); }
        	
        	return;
        }
    }
    
    public void SeguirRoboSalvador(){
    	//System.out.println("SeguirRoboSalvador");
    	_imj.atualizarDadosRobosViaTCP();
    	if (_imj.getR1() == null || _imj.getR2() == null) {return;}

    	if (campoSeguroRefem()) {
    		_esr.addPercept(_imj.getAgInimigo(), Literal.parseLiteral("pararBloquear."));
    		_esr.removePercept(_imj.getAgInimigo(), Literal.parseLiteral("bloquear."));
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
    		_esr.removePercept(_imj.getAgInimigo(), Literal.parseLiteral("agenteProximo."));
    		_esr.addPercept(_imj.getAgSave(), Literal.parseLiteral("voltei.")); return; 
    	}
    	
    	List<String> ordens = new CalculosRoboCode(_imj).getOrdensVoltarMinhaArea();
    	if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 0);
    }
    
    public void BloquearAgSave() throws Exception {
        if (volteiMinhaArea()) {
    		_esr.removePercept(_imj.getAgInimigo(), Literal.parseLiteral("agenteProximo."));
    		_esr.addPercept(_imj.getAgSave(), Literal.parseLiteral("voltei.")); return; 
    	}
       
        List<String> ordens = new CalculosRoboCode(_imj).getOrdensBloquearAgSave();
        if (ordens != null && ordens.size() > 0) enviarOrdem(ordens, 2); //envia ordens ao agente inimigo
    }
    
    private boolean pertoInimigo() {
    	if (_imj.getR3()==null) return false;
    	return new CalculosRoboCode(_imj).getDistanciaInimigo() <= 300.0;
    }
    private boolean pertoRefem() {
    	return new CalculosRoboCode(_imj).getDistanciaRefem() <= 80.0;
    }
    private boolean volteiMinhaArea(){
    	return CalculoVetores.distanciaPontos(
    			_imj.getR1().getX(), _imj.getR1().getY(),_imj.getXF(), _imj.getYF()) <= 20;
    }
    private boolean campoSeguroRefem(){
    	return _imj.getR2().getX() >= 400 && _imj.getR2().getY() >= 500 && pertoRefem();
    }
    
    private void enviarOrdem(List<String> ordens, int indice){
    	EnviarMsgTCP en = new EnviarMsgTCP(TipoEnvio.enviarOrdens, _imj.getPorta(indice), _imj.getIp(), _imj, indice);
    	en.setOrdens(ordens);
    	en.start();
    }
    
}
