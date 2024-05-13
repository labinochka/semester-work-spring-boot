package ru.kpfu.itis.beerokspring.service;

import ru.kpfu.itis.beerokspring.dto.response.ShortInfoAccountResponse;

import java.util.List;

public interface AdminService {

    void addAdmin(String username);

    List<ShortInfoAccountResponse> getAdmins();

    void deleteAdmin(String username);
}
