package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.InvalidChaveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Chave;
import model.Enum_chave;
import model.Usuario;

public class UsuarioDAO {

	  public static void persistirContato(Usuario usuario) {
		  
	        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/agenda_pix", "root", "12345678")) {
	            String sql = "INSERT INTO CONTATO_PIX (nome, chave_pix, tipo_chave) VALUES (?,?,?)";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, usuario.getNome());
	            statement.setString(2, usuario.getChavePIX().getChave());
	            statement.setString(3, usuario.getChavePIX().getStringTipo());
	            

	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	  
	  public static boolean cadastrarContato(String nome, String tipoChave, String chave){
	        Chave chavePIX = new Chave();
	        chavePIX.setTipo(Enum_chave.valueOf(tipoChave));
	        try {
				chavePIX.setChave(chave);
			} catch (InvalidChaveException e) {
				
				return false;
			}

	        Usuario usuario = new Usuario();
	        usuario.setNome(nome);
	        usuario.setChavePIX(chavePIX);

	        persistirContato(usuario);
	        return true;
	    }
      public static ObservableList<Usuario> obterUsuariosDoBanco() {
    	  ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

          try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/agenda_pix", "root", "12345678")) {
              String sql = "SELECT nome, tipo_chave, chave_pix FROM CONTATO_PIX";
              PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery();

              while (resultSet.next()) {
                  String nome = resultSet.getString("nome");
                  String tipoChave = resultSet.getString("tipo_chave");
                  String chavePix = resultSet.getString("chave_pix");
                  
                  Chave chave = new Chave();
                  chave.setTipo(Enum_chave.valueOf(tipoChave));
                  try {
					chave.setChave(chavePix);
				} catch (InvalidChaveException e) {
					
				}
                  
                  Usuario usuario = new Usuario();
                  usuario.setNome(nome);
                  usuario.setChavePIX(chave);

                  usuarios.add(usuario);
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }

          return usuarios;
      }
      };



