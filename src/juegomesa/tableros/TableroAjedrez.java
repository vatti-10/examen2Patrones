/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegomesa.tableros;

import juegomesa.emuns.EColorJugador;
import juegomesa.emuns.ETipoFicha;
import juegomesa.emuns.ETipoJuego;
import juegomesa.fichas.Ficha;
import juegomesa.utils.FabricaConvertidoresInputCoordenada;

/**
 *
 * @author carlo
 */
public class TableroAjedrez extends Tablero{

    public TableroAjedrez() {
        super();
    }
    
    @Override
    protected void construirCasillasTablero() {
        casillasTablero=new Casilla[8][8];
        rellarTableroConCasillas();
        ubicarFichas();
    }

    @Override
    protected int[] getCoordenadasNumericas(String pCoordenadas) {
        return FabricaConvertidoresInputCoordenada.fabricarConvertidor(ETipoJuego.AJEDREZ).convertir(pCoordenadas);
    }

    @Override
    public boolean actualizarTablero(String pCoordenadas,EColorJugador pColorJugador) {
        int [] coordenadas=getCoordenadasNumericas(pCoordenadas);
        if(verificarCoordenadas(pCoordenadas)&&estaCasillaVacia(coordenadas[0], coordenadas[1])){
            return realizarJugada(pCoordenadas,pColorJugador);
        }
        return false;
    }

    @Override
    protected boolean verificarCoordenadas(String pCoordenadas) {
        return validarCoordenadas(getCoordenadasNumericas(pCoordenadas));
    }
    
    @Override
    protected boolean validarCoordenadas(int[] pCoordenadas){
        return pCoordenadas[0]<getCasillasTablero()[0].length
                && pCoordenadas[1]<getCasillasTablero().length;
    }
    
    private void ubicarFichas(){
        ubicarFichasBlancas();
        ubicarFichasNegras();
    }

    private void ubicarFichasBlancas() {
        getCasillasTablero()[0][0].setFicha(new Ficha(0, 0, ETipoFicha.TORRE, EColorJugador.BLANCO));
        getCasillasTablero()[0][7].setFicha(new Ficha(0, 0, ETipoFicha.TORRE, EColorJugador.BLANCO));
        getCasillasTablero()[0][1].setFicha(new Ficha(0, 0, ETipoFicha.CABALLO, EColorJugador.BLANCO));
        getCasillasTablero()[0][6].setFicha(new Ficha(0, 0, ETipoFicha.CABALLO, EColorJugador.BLANCO));
        getCasillasTablero()[0][2].setFicha(new Ficha(0, 0, ETipoFicha.ARFIL, EColorJugador.BLANCO));
        getCasillasTablero()[0][5].setFicha(new Ficha(0, 0, ETipoFicha.ARFIL, EColorJugador.BLANCO));
        getCasillasTablero()[0][3].setFicha(new Ficha(0, 0, ETipoFicha.REINA, EColorJugador.BLANCO));
        getCasillasTablero()[0][4].setFicha(new Ficha(0, 0, ETipoFicha.REY, EColorJugador.BLANCO));
        ubicarPeones(1);
    }

    private void ubicarFichasNegras() {
        getCasillasTablero()[0][0].setFicha(new Ficha(0, 0, ETipoFicha.TORRE, EColorJugador.BLANCO));
        getCasillasTablero()[0][7].setFicha(new Ficha(0, 0, ETipoFicha.TORRE, EColorJugador.BLANCO));
        getCasillasTablero()[0][1].setFicha(new Ficha(0, 0, ETipoFicha.CABALLO, EColorJugador.BLANCO));
        getCasillasTablero()[0][6].setFicha(new Ficha(0, 0, ETipoFicha.CABALLO, EColorJugador.BLANCO));
        getCasillasTablero()[0][2].setFicha(new Ficha(0, 0, ETipoFicha.ARFIL, EColorJugador.BLANCO));
        getCasillasTablero()[0][5].setFicha(new Ficha(0, 0, ETipoFicha.ARFIL, EColorJugador.BLANCO));
        getCasillasTablero()[0][3].setFicha(new Ficha(0, 0, ETipoFicha.REINA, EColorJugador.BLANCO));
        getCasillasTablero()[0][4].setFicha(new Ficha(0, 0, ETipoFicha.REY, EColorJugador.BLANCO));
        ubicarPeones(6);
    }
    private void ubicarPeones(int pFila){
        for (int i = 0; i < getCasillasTablero().length; i++) {
            getCasillasTablero()[pFila][i].setFicha(new Ficha(0, 0, ETipoFicha.PEON, EColorJugador.BLANCO));
        }
    }

    private boolean realizarJugada(String pCoordenadas,EColorJugador pColorJugador) {
        if(pCoordenadas.length()==2 || Character.isLowerCase(pCoordenadas.charAt(0))){
            return realizarJugadaConPeon(pCoordenadas,pColorJugador);
        }else if(pCoordenadas.length()>=3 &&pCoordenadas.charAt(0)=='B'){
            return verificarJugada(pCoordenadas,pColorJugador,ETipoFicha.ARFIL);
        }
        else if(pCoordenadas.length()>=3 &&pCoordenadas.charAt(0)=='R'){
            return verificarJugada(pCoordenadas,pColorJugador,ETipoFicha.TORRE);
        }
        else if(pCoordenadas.length()>=3 &&pCoordenadas.charAt(0)=='N'){
            return verificarJugada(pCoordenadas,pColorJugador,ETipoFicha.CABALLO);
        }
        else if(pCoordenadas.length()>=3 &&pCoordenadas.charAt(0)=='Q'){
            return verificarJugada(pCoordenadas,pColorJugador,ETipoFicha.REINA);
        }
        else if(pCoordenadas.length()>=3 &&pCoordenadas.charAt(0)=='K'){
            return verificarJugada(pCoordenadas,pColorJugador,ETipoFicha.REY);
        }
        return false;
    }
    private boolean verificarJugada(String pCoordenadas, EColorJugador pColorJugador, ETipoFicha pTipoFicha) {
        if(pCoordenadas.charAt(1)=='x'){
            return comerFicha(pCoordenadas,pColorJugador,pTipoFicha);
        }else if(pCoordenadas.length()==3){
            return moverFicha(pCoordenadas,pColorJugador,pTipoFicha);
        }
        return false;
    }
    
    private boolean moverFicha(String pCoordenadas, EColorJugador pColorJugador, ETipoFicha pTipoFicha) {
        int [] coordenadas=getCoordenadasNumericas(pCoordenadas);
        if(estaCasillaVacia(coordenadas[0],coordenadas[1])){
            return buscarFicha(coordenadas, pColorJugador, pTipoFicha);
        }
        return false;
    }
    
    private boolean realizarJugadaConPeon(String pCoordenadas,EColorJugador pColorJugador){
        if(pCoordenadas.length()==2){
            return moverPeon(pCoordenadas,pColorJugador);
        }else if(pCoordenadas.charAt(1)=='x'){
            return comerFicha(pCoordenadas,pColorJugador,ETipoFicha.PEON);
        }
        return false;
    }
    private boolean moverPeon(String pCoordenadas, EColorJugador pColorJugador) {
        int [] coordenadas=getCoordenadasNumericas(pCoordenadas);
        if(pColorJugador==EColorJugador.BLANCO && !estaCasillaVacia(coordenadas[0],coordenadas[1]-1) &&
                getCasillasTablero()[coordenadas[1]-1][coordenadas[0]].getFicha().moverFicha(coordenadas)){
            
                getCasillasTablero()[coordenadas[1]][coordenadas[0]].setFicha(getFicha(coordenadas[0],coordenadas[1]-1));
                getCasillasTablero()[coordenadas[0]][coordenadas[1]-1].setFicha(null);
                return true;
                
        }else if(!estaCasillaVacia(coordenadas[0],coordenadas[1]+1)&& getCasillasTablero()[coordenadas[1]+1][coordenadas[0]].getFicha().moverFicha(coordenadas)){
            
                getCasillasTablero()[coordenadas[1]][coordenadas[0]].setFicha(getFicha(coordenadas[0],coordenadas[1]+1));
                getCasillasTablero()[coordenadas[0]][coordenadas[1]+1].setFicha(null);
                return true;
        }
        return false;
    }

    private boolean comerFicha(String pCoordenadas, EColorJugador pColorJugador,ETipoFicha pTipoFicha) {
        String[] coordenadasString = pCoordenadas.split("x");
        int [] coordenadas=getCoordenadasNumericas(coordenadasString[1]);
        if(!estaCasillaVacia(coordenadas[0],coordenadas[1])&& getFicha(coordenadas[0],coordenadas[1]).getColor()!=pColorJugador){
            return buscarFicha(coordenadas,pColorJugador,pTipoFicha);
        }else
            return false;
    }

    private boolean buscarFicha(int[] coordenadas, EColorJugador pColorJugador,ETipoFicha pTipoFicha) {
        for (int i = 0; i < getCasillasTablero().length; i++) {
            for (int j = 0; j < getCasillasTablero()[i].length; j++) {
                if (!estaCasillaVacia(j, i) && getFicha(j, i).getColor()==pColorJugador &&getFicha(j, i).getTipoFicha()==pTipoFicha && getFicha(j, i).moverFicha(coordenadas)) {
                    getCasillasTablero()[coordenadas[1]][coordenadas[0]].setFicha(getFicha(j, i));
                    getCasillasTablero()[i][j].setFicha(null);
                    return true;
                }
            }
        }
        return false;
    }
}