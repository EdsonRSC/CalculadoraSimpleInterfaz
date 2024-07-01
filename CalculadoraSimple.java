// Importamos los paquetes necesarios para manejar eventos, trabajar con pilas y crear la interfaz gráfica
import java.awt.event.*;
import java.util.Stack;
import javax.swing.*;

// Definimos la clase Calculadora que extiende JFrame e implementa ActionListener para manejar eventos
public class Calculadora extends JFrame implements ActionListener {
    // Declaramos un array de botones y un campo de texto para la pantalla
    JButton botones[];
    JTextField pantalla = new JTextField();

    // Constructor de la clase Calculadora
    public Calculadora() {
        // Establecemos el layout como nulo para posicionar componentes manualmente
        setLayout(null);

        // Definimos las etiquetas de los botones de la calculadora
        String[] etiquetas = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", 
        "*", "/", "=", "AC", "C"};
        botones = new JButton[etiquetas.length];

        // Definimos las posiciones (x, y) de cada botón en la interfaz
        int[][] posiciones = {
            {115, 200}, {10, 165}, {115, 165}, {220, 165},
            {10, 130}, {115, 130}, {220, 130}, {10, 95},
            {115, 95}, {220, 95}, {325, 130}, {325, 165},
            {325, 95}, {325, 200}, {220, 200}, {10, 60}, {115, 60}
        };

        // Creamos y añadimos los botones a la interfaz, y les asignamos un ActionListener
        for (int i = 0; i < etiquetas.length; i++) {
            botones[i] = new JButton(etiquetas[i]);
            botones[i].setActionCommand("boton" + etiquetas[i]);
            botones[i].setBounds(posiciones[i][0], posiciones[i][1], 100, 30);
            add(botones[i]);
            botones[i].addActionListener(this);
        }

        // Inicializamos la pantalla de la calculadora y la añadimos a la interfaz
        pantalla.setBounds(10, 10, 415, 40);
        pantalla.setEditable(false); // Hacemos la pantalla no editable
        add(pantalla);
    }

    // Método para manejar eventos de acción
    public void actionPerformed(ActionEvent e) {
        // Obtenemos el comando de acción del evento
        String accion = e.getActionCommand();

        // Usamos un switch para determinar qué hacer según el botón presionado
        switch (accion) {
            case "boton0":
                pantalla.setText(pantalla.getText() + "0");
                break;
            case "boton1":
                pantalla.setText(pantalla.getText() + "1");
                break;
            case "boton2":
                pantalla.setText(pantalla.getText() + "2");
                break;
            case "boton3":
                pantalla.setText(pantalla.getText() + "3");
                break;
            case "boton4":
                pantalla.setText(pantalla.getText() + "4");
                break;
            case "boton5":
                pantalla.setText(pantalla.getText() + "5");
                break;
            case "boton6":
                pantalla.setText(pantalla.getText() + "6");
                break;
            case "boton7":
                pantalla.setText(pantalla.getText() + "7");
                break;
            case "boton8":
                pantalla.setText(pantalla.getText() + "8");
                break;
            case "boton9":
                pantalla.setText(pantalla.getText() + "9");
                break;
            case "boton+":
                pantalla.setText(pantalla.getText() + "+");
                break;
            case "boton-":
                pantalla.setText(pantalla.getText() + "-");
                break;
            case "boton*":
                pantalla.setText(pantalla.getText() + "*");
                break;
            case "boton/":
                pantalla.setText(pantalla.getText() + "/");
                break;
            case "boton=":
                evaluarExpresion();
                break;
            case "botonAC":
                pantalla.setText("");
                break;
            case "botonC":
                String texto = pantalla.getText();
                if (!texto.isEmpty()) {
                    pantalla.setText(texto.substring(0, texto.length() - 1));
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    // Método para evaluar la expresión matemática en la pantalla
    private void evaluarExpresion() {
        String expresion = pantalla.getText();
        try {
            // Convertimos la expresión infija a postfija
            String postfijo = convertirAPostfijo(expresion);
            // Evaluamos la expresión postfija
            double resultado = evaluarPostfijo(postfijo);
            // Mostramos el resultado en la pantalla
            pantalla.setText(String.valueOf(resultado));
        } catch (Exception e) {
            pantalla.setText("Error");
        }
    }

    // Método para convertir una expresión infija a notación postfija
    private String convertirAPostfijo(String expresion) {
        Stack<Character> pila = new Stack<>();
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Si el carácter es un dígito, lo añadimos al resultado
            if (Character.isDigit(c)) {
                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    resultado.append(expresion.charAt(i++));
                }
                resultado.append(' ');
                i--;
            } else if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    resultado.append(pila.pop()).append(' ');
                }
                pila.pop();
            } else if (esOperador(c)) {
                while (!pila.isEmpty() && tienePrecedencia(pila.peek(), c)) {
                    resultado.append(pila.pop()).append(' ');
                }
                pila.push(c);
            }
        }
        while (!pila.isEmpty()) {
            resultado.append(pila.pop()).append(' ');
        }
        return resultado.toString();
    }

    // Método para verificar si un carácter es un operador
    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Método para verificar la precedencia de los operadores
    private boolean tienePrecedencia(char op1, char op2) {
        if (op1 == '(' || op1 == ')')
            return false;
        if ((op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-'))
            return false;
        else
            return true;
    }

    // Método para evaluar una expresión en notación postfija
    private double evaluarPostfijo(String expresion) {
        Stack<Double> pila = new Stack<>();
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // Si el carácter es un dígito, lo convertimos a número y lo añadimos a la pila
            if (Character.isDigit(c)) {
                StringBuilder sbuf = new StringBuilder();
                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    sbuf.append(expresion.charAt(i++));
                }
                pila.push(Double.parseDouble(sbuf.toString()));
                i--;
            } else if (esOperador(c)) {
                double val2 = pila.pop();
                double val1 = pila.pop();
                switch (c) {
                    case '+':
                        pila.push(val1 + val2);
                        break;
                    case '-':
                        pila.push(val1 - val2);
                        break;
                    case '*':
                        pila.push(val1 * val2);
                        break;
                    case '/':
                        if (val2 == 0)
                            throw new UnsupportedOperationException("Division by zero");
                        pila.push(val1 / val2);
                        break;
                }
            }
        }
        return pila.pop();
    }

    // Método principal para ejecutar la calculadora
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        calculadora.setBounds(0, 0, 450, 285);
        calculadora.setVisible(true);
        calculadora.setLocationRelativeTo(null);
        calculadora.setResizable(false);
    }
}

