package com.gestion.tasking.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestion.tasking.entity.User;



@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User usuario = new User();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre_usuario"));
        usuario.setApellido(rs.getString("apellido_usuario"));
        usuario.setDni(rs.getString("dni_usuario"));
        usuario.setUsername(rs.getString("username_usuario"));
        usuario.setEmail(rs.getString("email_usuario"));
        usuario.setPassword(rs.getString("password_usuario"));
        usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion_usuario").toLocalDateTime());
        return usuario;
    };

    @Override
    public Optional<User> buscarUsuarioPorUsernameOCorreo(String username, String email) {
        String sql = "SELECT * FROM tm_usuarios WHERE username_usuario = ? OR email_usuario = ?";
        
        try {
            User usuario = jdbcTemplate.queryForObject(sql, rowMapper, username, email);
            return Optional.ofNullable(usuario);
        } catch (DataAccessException e) {
            // Si no se encuentra el usuario, retornamos un Optional vacío
            return Optional.empty();
        }
    }

}
