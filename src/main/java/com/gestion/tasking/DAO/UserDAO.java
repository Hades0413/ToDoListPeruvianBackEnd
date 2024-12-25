package com.gestion.tasking.DAO;

import java.util.Optional;

import com.gestion.tasking.entity.User;



public interface UserDAO {

    Optional<User> buscarUsuarioPorUsernameOCorreo(String username, String email);
}
