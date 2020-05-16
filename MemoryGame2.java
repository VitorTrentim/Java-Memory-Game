/*
    Feito por Vitor Trentim para aprendizado da linguagem Java
    Abril, 2020
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.lang.Math;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.*;

class MemoryGame2 extends JFrame implements ActionListener {
    JPanel painelCartas;
    JPanel pMenu;
    JPanel pFechar;
    JPanel pPtsTntvs;
    JPanel painelFechar;
    GridBagConstraints gbc;
    JLabel labPontuacao;
    JLabel labTentativas;
    JButton bFechar;
    Border compound;
    TitledBorder titlePontuacao, titleTentativas, titleTempo;

        //Tempo
    Timer timer;
    long timeElapsed, tempo = 0;
    JLabel labTempo = new JLabel(" " + Long.toString(tempo) + " segundo(s) ");


    Cartas deck[] = new Cartas[25];
    Cartas compara1, compara2;
    int cartasAbertas, pontos, tentativas;
    boolean aguardaCronometro = false;
    public MemoryGame2(){
        super("Memory Game");
        setPreferredSize(new Dimension(1000, 725));
        setMinimumSize(new Dimension(1000, 725));
        setMaximumSize(new Dimension(1000, 725));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(225, 255, 255));
        setLayout(new GridBagLayout());

        compound = BorderFactory.createTitledBorder(compound, " ",TitledBorder.CENTER,TitledBorder.ABOVE_TOP);
        compound = BorderFactory.createTitledBorder(compound, "Memory Game",TitledBorder.CENTER,TitledBorder.ABOVE_TOP);
        compound = BorderFactory.createTitledBorder(compound, " ",TitledBorder.CENTER,TitledBorder.ABOVE_TOP);
        compound = BorderFactory.createTitledBorder(compound, " ",TitledBorder.CENTER,TitledBorder.BELOW_BOTTOM);

        novoJogo();
    }

    private void createDeck(){

        deck[0] = new Cartas("default","/Resources/default.png","/Resources/default.png", 0);
        deck[1] = new Cartas("Leao","/Resources/leao.jpg","/Resources/leaoV.jpg",1);
        deck[2] = new Cartas("Leao","/Resources/leao.jpg","/Resources/leaoV.jpg",1);
        deck[3] = new Cartas("Elefante","/Resources/elefante.jpg","/Resources/elefanteV.jpg",2);
        deck[4] = new Cartas("Elefante","/Resources/elefante.jpg","/Resources/elefanteV.jpg",2);
        deck[5] = new Cartas("Raposa","/Resources/raposa.jpg","/Resources/raposaV.jpg",3);
        deck[6] = new Cartas("Raposa","/Resources/raposa.jpg","/Resources/raposaV.jpg",3);
        deck[7] = new Cartas("Tigre","/Resources/tigre.jpg", "/Resources/tigreV.jpg",4);
        deck[8] = new Cartas("Tigre", "/Resources/tigre.jpg", "/Resources/tigreV.jpg",4);
        deck[9] = new Cartas("Veado","/Resources/veado.jpg", "/Resources/veadoV.jpg",5);
        deck[10] = new Cartas("Veado","/Resources/veado.jpg", "/Resources/veadoV.jpg",5);
        deck[11] = new Cartas("Lagarto","/Resources/lagarto.jpg", "/Resources/lagartoV.jpg",6);
        deck[12] = new Cartas("Lagarto","/Resources/lagarto.jpg", "/Resources/lagartoV.jpg",6);
        deck[13] = new Cartas("Morcego","/Resources/morcego.jpg", "/Resources/morcegoV.jpg",7);
        deck[14] = new Cartas("Morcego","/Resources/morcego.jpg", "/Resources/morcegoV.jpg",7);
        deck[15] = new Cartas("Coruja","/Resources/coruja.jpg", "/Resources/corujaV.jpg",8);
        deck[16] = new Cartas("Coruja","/Resources/coruja.jpg", "/Resources/corujaV.jpg",8);
        deck[17] = new Cartas("Macaco","/Resources/macaco.jpg","/Resources/macacoV.jpg",9);
        deck[18] = new Cartas("Macaco","/Resources/macaco.jpg","/Resources/macacoV.jpg",9);
        deck[19] = new Cartas("Girafa","/Resources/girafa.jpg","/Resources/girafaV.jpg",10);
        deck[20] = new Cartas("Girafa","/Resources/girafa.jpg","/Resources/girafaV.jpg",10);
        deck[21] = new Cartas("Pinguim ","/Resources/pinguim.jpg","/Resources/pinguimV.jpg",11);
        deck[22] = new Cartas("Pinguim","/Resources/pinguim.jpg","/Resources/pinguimV.jpg",11);
        deck[23] = new Cartas("Pato","/Resources/pato.jpg","/Resources/patoV.jpg",12);
        deck[24] = new Cartas("Pato","/Resources/pato.jpg","/Resources/patoV.jpg",12);
    }

    private void randomizeDeck(){
        createDeck();
        double randomDouble;
        int randomInt, numCartasAdd = 0, x=37, y=8;
        Insets insets = painelCartas.getInsets();
        Dimension size;
        while (numCartasAdd < 24){
            randomDouble = Math.random() * 24 + 1;
            randomInt = (int) randomDouble;

            if (deck[randomInt].inserida == true)
                continue;
            else {
                deck[randomInt].inserida = true;
                painelCartas.add(deck[randomInt]);
                numCartasAdd++;

                System.out.println("location: " + x + "," + y);
                size = deck[randomInt].getPreferredSize();
                deck[randomInt].setBounds(x + insets.left, y + insets.top,
                        size.width, size.height);
                x+=125;
                if(x>700){
                    x = 37;
                    y += 170;
                }
            }
        }

    }

    class Cartas extends JPanel implements MouseListener{
        int match;
        JLabel imagem;
        JLabel legenda;
        String caminhoInterno, caminhoMarcada, nomeInterno;

        boolean inserida = false;
        boolean isMatched = false;
        boolean CartaJaUtilizada = false;
        boolean isTurned = false;

        public Cartas(String nome, String caminho, String marcada, int vmatch){
            super(new BorderLayout());
            caminhoInterno = caminho;
            caminhoMarcada = marcada;
            nomeInterno = nome;
            match = vmatch;
            setBackground(new Color(100,225,100));
            mostrarCostas();

            setVisible(true);
        }

        void mostrarCostas(){
            if (CartaJaUtilizada == true){
                CartaJaUtilizada = false;
                remove(imagem);
                remove(legenda);
            }
            imagem = new JLabel(new ImageIcon(Cartas.class.getResource("/Resources/default.png")));
            imagem.addMouseListener(this);
            legenda = new JLabel(" ", JLabel.CENTER);
            add(imagem,BorderLayout.CENTER);
            add(legenda,BorderLayout.SOUTH);
            this.repaint();
            revalidate();
        }

        void mostrarFrente(){
            CartaJaUtilizada = true;
            remove(imagem);
            remove(legenda);
            legenda.setText(nomeInterno);
            imagem = new JLabel(new ImageIcon(Cartas.class.getResource(caminhoInterno)));
            add(imagem,BorderLayout.CENTER);
            add(legenda,BorderLayout.SOUTH);
            this.repaint();
            revalidate();
        }

        void cartaPontuada(){
            this.isMatched = true;
            remove(imagem);
            remove(legenda);
            legenda.setText(nomeInterno);
            imagem = new JLabel(new ImageIcon(Cartas.class.getResource(caminhoMarcada)));
            add(imagem,BorderLayout.CENTER);
            add(legenda,BorderLayout.SOUTH);
            this.repaint();
            revalidate();
        }

        public void mouseClicked(MouseEvent e){
            if (aguardaCronometro){
                aguardaCronometro = false;
                iniciaTimer();
                return;
            }
            if (cartasAbertas == 0){
                compara1 = this;
                compara1.mostrarFrente();
                compara1.isTurned = true;
                cartasAbertas++;
                revalidate();
            } else {
                compara2 = this;
                compara2.mostrarFrente();
                compara2.isTurned = true;
                revalidate();

                comparaJogo(compara1,compara2);
            }
        }
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}

        private void comparaJogo(Cartas carta1, Cartas carta2){
            if (carta1.match == carta2.match){
                JOptionPane.showMessageDialog(painelCartas, "Encontrou!", "Parabens!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Cartas.class.getResource("/Resources/acertoMenor.png")));
                compara1.cartaPontuada();
                compara2.cartaPontuada();
                tentativas++;
                pontos ++;
                labPontuacao.setText("Pontos: " + pontos);
                if (pontos == 12){
                    fimDeJogo();
                } else {
                    cartasAbertas = 0;
                    labTentativas.setText("Tentativas : " + tentativas);
                    return;
                }
            }
            else {
                JOptionPane.showMessageDialog(painelCartas, "Par incorreto!", "Nao foi dessa vez", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Cartas.class.getResource("/Resources/erroMenor.png")));
                compara1.mostrarCostas();
                compara1.isTurned = false;
                compara2.mostrarCostas();
                compara2.isTurned = false;
                cartasAbertas = 0;
                labTentativas.setText("Tentativas : " + ++tentativas);
                return;
            }

        }

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bFechar){
            System.exit(0);
        }
    }

    private void novoJogo(){
        iniciaPaineis();

        pontos = 0; cartasAbertas = 0; tentativas = 0;
        labPontuacao.setText("Pontos: " + pontos);
        labTentativas.setText("Tentativas: " + tentativas);

        labPontuacao.repaint();
        labTentativas.repaint();

        revalidate();
        randomizeDeck();

        gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.gridx=1; gbc.gridy=1;
        add(painelCartas,gbc);
        gbc.weightx = 0.3;
        gbc.gridx=2;
        add(pMenu,gbc);
        revalidate();
        iniciaTimer();
    }

    void iniciaPaineis(){
        painelCartas = new JPanel(null);
        painelCartas.setPreferredSize(new Dimension(800,700));
        painelCartas.setMinimumSize(new Dimension(800,700));
        painelCartas.setMaximumSize(new Dimension(800,700));
        painelCartas.setBackground(new Color(100,225,100));
        painelCartas.setBorder(BorderFactory.createLineBorder(Color.black));

        pFechar = new JPanel();
        pPtsTntvs = new JPanel(new GridLayout(8,1,0,0));

        labPontuacao = new JLabel();
        labTentativas = new JLabel();

        labPontuacao.setHorizontalAlignment(JLabel.CENTER);
        labTentativas.setHorizontalAlignment(JLabel.CENTER);
        labTempo.setHorizontalAlignment(JLabel.CENTER);

        titleTempo = BorderFactory.createTitledBorder("Tempo");
        labTempo.setBackground(new Color(200,255,210));
        labTempo.setForeground(new Color(125,125,175));
        labTempo.setBorder(titleTempo);
        labTempo.setOpaque(true);


        titlePontuacao = BorderFactory.createTitledBorder("Pontos");
        labPontuacao.setBackground(new Color(200,255,210));
        labPontuacao.setForeground(new Color(125,125,175));
        labPontuacao.setBorder(titlePontuacao);
        labPontuacao.setOpaque(true);


        titleTentativas = BorderFactory.createTitledBorder("Tentativas");
        labTentativas.setBackground(new Color(200,255,210));
        labTentativas.setForeground(new Color(125,125,175));
        labTentativas.setBorder(titleTentativas);
        labTentativas.setOpaque(true);


        pPtsTntvs.add(labTempo);
        pPtsTntvs.add(labTentativas);
        pPtsTntvs.add(labPontuacao);
        pPtsTntvs.setBackground(new Color(200,255,210));

        bFechar = new JButton("Fechar");
        bFechar.addActionListener(this);
        pFechar.add(bFechar);
        pFechar.setBackground(new Color(75,75,125));

        pMenu = new JPanel(new BorderLayout());
        pMenu.setPreferredSize(new Dimension(185,700));
        pMenu.setMinimumSize(new Dimension(185,700));
        pMenu.setMaximumSize(new Dimension(185,700));
        pMenu.setBorder(compound);
        pMenu.setBackground(new Color(150,150,200));
        pMenu.add(pPtsTntvs,BorderLayout.CENTER);
        pMenu.add(pFechar,BorderLayout.SOUTH);

    }

    void iniciaTimer(){
        int resposta = JOptionPane.showConfirmDialog(this, "Clique 'YES' para iniciar o cronometro!", "Preparado?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Cartas.class.getResource("/Resources/relogio.png")));
        if (resposta == JOptionPane.YES_OPTION){
            tempo = 0;
            timer = new Timer();
            labTempo.setText(" " + Long.toString(tempo) + " segundo(s) ");
            timer.schedule(new taskRelogio(), 1000, 1000);
        } else {
            aguardaCronometro = true;
        }
    }

    class taskRelogio extends TimerTask {
        public void run() {
            tempo++;
            labTempo.setText(" " + Long.toString(tempo) + " segundo(s) ");
        }
    }

    private void fimDeJogo(){
        timer.cancel();
        timer.purge();
        int resposta = JOptionPane.showConfirmDialog(this, "Tempo de jogo: " + tempo + " segundos\nTentativas: " + tentativas + "\nDeseja jogar novamente?", "Fim de jogo!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(Cartas.class.getResource("/Resources/fimJogo.png")));
        if (resposta == JOptionPane.YES_OPTION){
            remove(painelCartas);
            remove(pMenu);
            novoJogo();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        MemoryGame2 tela = new MemoryGame2();
    }
}
