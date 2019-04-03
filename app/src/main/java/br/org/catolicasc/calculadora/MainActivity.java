package br.org.catolicasc.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText resultado;
    private EditText novoNumero;
    private TextView textOperacaoPendente;
    private String operacaoPendente;
    private Double operando1 = null;
    private static final String ESTADO_OPERACAO_PENDENTE = "OperacaoPendente";
    private static final String ESTADO_OPERANDO1 = "Operando1";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ESTADO_OPERACAO_PENDENTE, operacaoPendente);
        if (operando1 != null) {
            outState.putDouble(ESTADO_OPERANDO1, operando1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        operacaoPendente = savedInstanceState.getString(ESTADO_OPERACAO_PENDENTE);
        operando1 = savedInstanceState.getDouble(ESTADO_OPERANDO1);
        textOperacaoPendente.setText(operacaoPendente);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultado = findViewById(R.id.resultado);
        novoNumero = findViewById(R.id.novoNumero);
        textOperacaoPendente = findViewById(R.id.operacao);

        // botões numéricos
        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonPonto = findViewById(R.id.buttonPonto);

        // botões de operação
        Button buttonIgual = findViewById(R.id.buttonIgual);
        Button buttonDividir = findViewById(R.id.buttonDividir);
        Button buttonMultiplicar = findViewById(R.id.buttonMultiplicar);
        Button buttonSubtrair = findViewById(R.id.buttonSubtrair);
        Button buttonSomar = findViewById(R.id.buttonSomar);

        View.OnClickListener listenerNumerico = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                novoNumero.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listenerNumerico);
        button1.setOnClickListener(listenerNumerico);
        button2.setOnClickListener(listenerNumerico);
        button3.setOnClickListener(listenerNumerico);
        button4.setOnClickListener(listenerNumerico);
        button5.setOnClickListener(listenerNumerico);
        button6.setOnClickListener(listenerNumerico);
        button7.setOnClickListener(listenerNumerico);
        button8.setOnClickListener(listenerNumerico);
        button9.setOnClickListener(listenerNumerico);
        buttonPonto.setOnClickListener(listenerNumerico);

        View.OnClickListener listenerOperacao = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String operacao = b.getText().toString();
                String numero = novoNumero.getText().toString();

                try {
                    Double valorDouble = Double.valueOf(numero);
                    executaOperacao(valorDouble, operacao);
                    mostraResultado();
                } catch (NumberFormatException e) {
                    novoNumero.setText("");
                }

                operacaoPendente = operacao;
                textOperacaoPendente.setText(operacaoPendente);
            }
        };

        buttonIgual.setOnClickListener(listenerOperacao);
        buttonDividir.setOnClickListener(listenerOperacao);
        buttonMultiplicar.setOnClickListener(listenerOperacao);
        buttonSubtrair.setOnClickListener(listenerOperacao);
        buttonSomar.setOnClickListener(listenerOperacao);

        Button buttonNegar = findViewById(R.id.buttonNegar);
        buttonNegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = novoNumero.getText().toString();
                if (numero.length() == 0){
                    novoNumero.setText("-");
                } else {
                    try {
                        Double valorDouble = Double.valueOf(numero);
                        valorDouble *= -1;
                        novoNumero.setText(valorDouble.toString());
                    } catch (NumberFormatException e) {
                        novoNumero.setText("");
                    }
                }
            }
        });
    }

    private void executaOperacao(Double numero, String operacao) {
        if (operando1 == null) {
            operando1 = numero;
        } else {
            if (operacaoPendente.equals("=")) {
                operacaoPendente = operacao;
            }

            switch (operacaoPendente) {
                case "=":
                    operando1 = numero;
                    break;
                case "/":
                    if (numero == 0) {
                        operando1 = 0.0;
                    } else {
                        operando1 /= numero;
                    }
                    break;
                case "*":
                    operando1 *= numero;
                    break;
                case "-":
                    operando1 -= numero;
                    break;
                case "+":
                    operando1 += numero;
                    break;
            }
        }
    }

    private void mostraResultado() {
        resultado.setText(operando1.toString());
        novoNumero.setText("");
    }


}
