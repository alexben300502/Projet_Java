package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainFrame extends JFrame{      //les Jcompsants sont positions et impémentation sont gérer par le .form
    private JPanel panel1;
    private JButton nouveauCompteButton;
    private JButton seConnecterButton;
    private JTextField textFieldemail;
    private JTextField passwordField1;
    private JCheckBox adminCheckBox;
    private JCheckBox clientCheckBox;
    private JButton accederButton;
    private JPanel login;
    private JPanel code;
    private JPanel nouveau;
    private JTextField textFieldnom;
    private JPanel finir;
    private JLabel Name;
    private JLabel Email;

    private JLabel Titre;
    private JLabel Passeword;


    //variable permetant de d'utiliser correctement la page et de stocker les informations
    private String role;
    private boolean admin=false;
    private boolean client=false;
    private boolean nouveau_compte=true;
    private String email;
    private String mdp;
    private String nom;
    private boolean tm=false;
    private boolean te=false;
    private boolean tmdp=false;
    private boolean tr=false;
    private Border originalBorder;
    private Border TextBorder;

    private int fin=-1;

public MainFrame() {
}

    //methode d'affichage
public int GUI_Connexion(){

    //rend des pannel transparent
    login.setOpaque(false);
    nouveau.setOpaque(false);
    code.setOpaque(false);
    finir.setOpaque(false);

    //affiche le Jframe
    setContentPane(panel1);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Singleton s=Singleton.getInstance();
    s.xm(screenSize.width);
    s.ym(screenSize.height);

    setSize(s.xm,s.ym);

    setLocationRelativeTo(null);
    setVisible(true);

    //ajoute de la couleur
    panel1.setBackground(new Color(17,26,49));
    clientCheckBox.setBackground(new Color(17,26,49));
    adminCheckBox.setBackground(new Color(17,26,49));

    Titre.setForeground(new Color(255,255,255));
    Name.setForeground(new Color(255,255,255));
    Email.setForeground(new Color(255,255,255));
    Passeword.setForeground(new Color(255,255,255));
    adminCheckBox.setForeground(new Color(255,255,255));
    clientCheckBox.setForeground(new Color(255,255,255));

    //cache des élements
    originalBorder = accederButton.getBorder();
    TextBorder=textFieldemail.getBorder();
    nom="";
    Titre.setText("");
    Name.setText("");
    Email.setText("");
    Passeword.setText("");
    cacher_bouton(accederButton);
    cacher_champ(textFieldnom);
    cacher_champ(textFieldemail);
    cacher_champ(passwordField1);
    cacher_case(clientCheckBox);
    cacher_case(adminCheckBox);

    //ajout d'écouteur sur les bouton
    nouveauCompteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //affiche les éléments pour une nouvelle inscription
            Titre.setText("NOUVEAU COMPTE");
            cacher_bouton(nouveauCompteButton);
            cacher_bouton(seConnecterButton);
            afficher_bouton(accederButton,"connecter");
            Name.setText("Nom");
            Email.setText("Email");
            Passeword.setText("Mot de passe");
            afficher_text(passwordField1,"");
            afficher_text(textFieldnom,"");
            afficher_text(textFieldemail,"");
            afficher_case(clientCheckBox);
            afficher_case(adminCheckBox);
        }
    });
    seConnecterButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //affiche les éléments pour une connexion
            Titre.setText("SE CONNECTER");
            nouveauCompteButton.setVisible(!nouveauCompteButton.isVisible());
            seConnecterButton.setVisible(!seConnecterButton.isVisible());
            afficher_bouton(accederButton,"connecter");
            nouveau_compte=false;
            Email.setText("Email");
            Passeword.setText("Mot de passe");
            afficher_text(textFieldemail,"");
            afficher_text(passwordField1,"");
        }
    });
    accederButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //vérifie que les élements sont bien rempli et permet retourner à la boucle de jeu
            if(nouveau_compte) {
                nom = textFieldnom.getText();
                email = textFieldemail.getText();
                mdp = passwordField1.getText();
                client = clientCheckBox.isSelected();
                admin = adminCheckBox.isSelected();

                if (!nom.isEmpty() && !email.isEmpty() && client != admin && !mdp.isEmpty()) {
                    Titre.setText("fini");
                    if (admin) {
                        role = "EMPLOYE";
                        //new Admin();
                    } else {
                        role = "CLIENT";
                        //new Page_Client();
                    }
                    fin=0;
                }
            }
            else{
                email = textFieldemail.getText();
                mdp = passwordField1.getText();
                if (!email.isEmpty()&& !mdp.isEmpty()){
                    Titre.setText("fini");
                    fin=1;
                }
            }
        }
    });

    do {
        System.out.println(fin);
        repaint();
    }while (this.fin==-1);

    System.out.println(fin);

    //fin:0:inscription
    //fin:1:connection
    return this.fin;
}

    //méthode pour cacher des éléments sans modifier la position des élements
    public void cacher_bouton(JButton bouton){
        bouton.setIcon(null);
        bouton.setText("");
        bouton.setContentAreaFilled(false);
        bouton.setBorder(null);
    }
    public void cacher_champ(JTextField champ) {
        champ.setText(""); // clear the text
        champ.setEditable(false); // disable editing
        champ.setOpaque(false); // make the background transparent
        champ.setBorder(null); // remove the border
    }

    public void cacher_case(JCheckBox caseACocher) {
        caseACocher.setSelected(false); // uncheck the checkbox
        caseACocher.setEnabled(false); // disable the checkbox
        caseACocher.setVisible(false); // hide the checkbox
    }

    //méthode pour afficher des éléments sans modifier la position des élements
    public void afficher_bouton(JButton bouton, String text){
        bouton.setText(text);
        bouton.setContentAreaFilled(true);
        bouton.setBorder(originalBorder);
    }

    public void afficher_text(JTextField area, String text){
        area.setText(text);
        area.setEditable(true); // disable editing
        area.setOpaque(true); // make the background transparent
        area.setBorder(TextBorder); // remove the border
    }

    public void afficher_case(JCheckBox case_){
        case_.setSelected(false); // uncheck the checkbox
        case_.setEnabled(true); // disable the checkbox
        case_.setVisible(true); // hide the checkbox
    }

    public String get_nom(){
        return nom;
    }
    public String get_email(){
        return email;
    }
    public String get_role(){
        return role;
    }
    public String get_mdp(){
        return mdp;
    }
}
