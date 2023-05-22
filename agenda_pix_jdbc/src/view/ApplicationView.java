package view;

import controller.UsuarioDAO;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;

public class ApplicationView extends Application {

	private Stage janela;
	private Scene main;
	private Scene cadastroChave;
	private Scene cadastroContato;
	private Scene visualizarContatos;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		janela = primaryStage;

		criarMain();
		criarCadastroContato();
		criarViewContato();

		janela.setScene(main);
		janela.show();
	}

	private void criarMain() {

		// Cria
		Label labelMensagem = new Label("Bem-vindo a agenda PIX!");
		labelMensagem.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

		Button btnAdicionarContato = new Button("Adicionar contato");
		Button btnVisualizarContatos = new Button("Ver a lista de contatos");

		// Atribui a ação tomada

		btnAdicionarContato.setOnAction(event -> showAddContato());
		btnVisualizarContatos.setOnAction(event -> showViewContato());

		// Adiciona esses itens na tela

		VBox layout = new VBox(20, labelMensagem, btnAdicionarContato, btnVisualizarContatos);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));
		main = new Scene(layout, 300, 400);

	}

	private void criarCadastroContato() {
		    Label labelTitulo = new Label("Cadastro de Novo Contato");
	        labelTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
	         
		    Label labelNome = new Label("Nome:");
	        TextField campoNome = new TextField();
	        Label labelTipoChave = new Label("Selecione o tipo de chave:");
	        ComboBox<String> comboBoxTipoChave = new ComboBox<>();
	        comboBoxTipoChave.getItems().addAll("CPF", "TELEFONE", "EMAIL");
	        Label labelChave = new Label("Chave:");
	        TextField campoChave = new TextField();
	        Button botaoConfirmar = new Button("Confirmar");
	        Button botaoVoltar = new Button("Voltar para tela anterior");

	        // Cria o layout da tela
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25));
	        grid.add(labelTitulo, 0, 0, 2, 1); // Adiciona o labelTitulo ocupando 2 colunas
	        grid.add(labelNome, 0, 1);
	        grid.add(campoNome, 1, 1);
	        grid.add(labelTipoChave, 0, 2);
	        grid.add(comboBoxTipoChave, 1, 2);
	        grid.add(labelChave, 0, 3);
	        grid.add(campoChave, 1, 3);

	        HBox hbox = new HBox(10);
	        hbox.setAlignment(Pos.BOTTOM_LEFT);
	        hbox.getChildren().addAll(botaoConfirmar, botaoVoltar);
	        grid.add(hbox, 0, 4, 2, 1);

	        BorderPane root = new BorderPane();
	        root.setCenter(grid);
	        root.setStyle("-fx-background-color: #f8f8f8;");

	        cadastroContato = new Scene(root, 400, 250);

//	     Configura o evento do botão
		botaoConfirmar.setOnAction(event -> {

			String Stringnome = campoNome.getText();
			String tipoChave = comboBoxTipoChave.getValue();
			String StringChave = campoChave.getText().replaceAll("\\s", "");
			
			if (!Stringnome.isEmpty() && !tipoChave.isEmpty() && !StringChave.isEmpty()) {
				
		   if(UsuarioDAO.cadastrarContato(Stringnome, tipoChave, StringChave)) {
			   Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Atenção!");
		        alert.setHeaderText(null);
		        alert.setContentText("Contato adicionado com sucesso!");

		        alert.showAndWait();
		   }else {
			   Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Atenção!");
		        alert.setHeaderText(null);
		        alert.setContentText("Insira um " + tipoChave + " válido!");

		        alert.showAndWait();
		   }
		   
		   criarViewContato();
			
		
				
			
			

			campoNome.clear();
			comboBoxTipoChave.getSelectionModel().clearSelection();
			campoChave.clear();
			
			}else {
				 Alert alert = new Alert(AlertType.INFORMATION);
			        alert.setTitle("Atenção!");
			        alert.setHeaderText(null);
			        alert.setContentText("Campos não preenchidos!");

			        alert.showAndWait();
				
			}

		});
		botaoVoltar.setOnAction(event -> {
			janela.setScene(main);
		});
		;
	}

	public void criarViewContato() {
	    Button botaoVoltar = new Button("Voltar para tela anterior");
	    botaoVoltar.setOnAction(e -> {
	        janela.setScene(main);
	    });

	    Label labelTitulo= new Label("Contatos adicionados:");
		labelTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
	    TableView<Usuario> tableView = new TableView<>();

	    // Configuração das colunas
	    TableColumn<Usuario, String> nomeColumn = new TableColumn<>("Nome");
	    TableColumn<Usuario, String> tipoChaveColumn = new TableColumn<>("Tipo de Chave");
	    TableColumn<Usuario, String> chavePixColumn = new TableColumn<>("Chave PIX");

	    nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
	    tipoChaveColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChavePIX().getStringTipo()));
	    chavePixColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getChavePIX().getChave()));

	    // Definição das larguras das colunas
	    nomeColumn.setMinWidth(150);
	    tipoChaveColumn.setMinWidth(150);
	    chavePixColumn.setMinWidth(150);

	    // Adiciona as colunas à tabela
	    tableView.getColumns().addAll(nomeColumn, tipoChaveColumn, chavePixColumn);

	    // Dados da tabela
	    ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
	    usuarios.addAll(UsuarioDAO.obterUsuariosDoBanco());

	    tableView.setPrefWidth(460);
	    tableView.setPrefHeight(400);
	    tableView.setItems(usuarios);

	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER);
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(10));

	    grid.add(tableView, 0, 0);

	    VBox vbox = new VBox(10);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.getChildren().addAll(labelTitulo, grid, botaoVoltar);
	    vbox.setPadding(new Insets(10, 0, 20, 0));

	    visualizarContatos = new Scene(vbox, 600, 400);
	}


	private void showAddContato() {
		janela.setScene(cadastroContato);
	}

	private void showViewContato() {
		janela.setScene(visualizarContatos);
	}

}
