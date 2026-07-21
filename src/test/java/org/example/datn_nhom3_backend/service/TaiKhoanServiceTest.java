package org.example.datn_nhom3_backend.service;

import org.example.datn_nhom3_backend.dto.TaiKhoanRequest;
import org.example.datn_nhom3_backend.entity.TaiKhoan;
import org.example.datn_nhom3_backend.entity.VaiTro;
import org.example.datn_nhom3_backend.repository.TaiKhoanRepository;
import org.example.datn_nhom3_backend.repository.VaiTroRepository;
import org.example.datn_nhom3_backend.service.impl.TaiKhoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaiKhoanServiceTest {

    @Mock
    private TaiKhoanRepository repository;
    @Mock
    private VaiTroRepository vaiTroRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TaiKhoanServiceImpl taiKhoanService;

    private VaiTro vaiTro;
    private TaiKhoan taiKhoan;

    @BeforeEach
    void setUp() {
        vaiTro = new VaiTro();
        vaiTro.setId(1);
        vaiTro.setMaVaiTro("ADMIN");
        vaiTro.setTenVaiTro("Quản trị viên");

        taiKhoan = new TaiKhoan();
        taiKhoan.setMatk(1);
        taiKhoan.setTendangnhap("admin");
        taiKhoan.setMatkhau("$2a$10$encodedPassword");
        taiKhoan.setHoten("Admin");
        taiKhoan.setEmail("admin@example.com");
        taiKhoan.setVaitro(vaiTro);
        taiKhoan.setTrangthai("ACTIVE");
    }

    @Test
    void getAll_ShouldReturnList() {
        when(repository.findAll()).thenReturn(java.util.List.of(taiKhoan));

        var result = taiKhoanService.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void getById_WhenExists_ShouldReturn() {
        when(repository.findById(1)).thenReturn(Optional.of(taiKhoan));

        var result = taiKhoanService.getById(1);

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getTendangnhap());
    }

    @Test
    void save_ShouldReturnSavedAccount() {
        when(repository.save(taiKhoan)).thenReturn(taiKhoan);

        var result = taiKhoanService.save(taiKhoan);

        assertNotNull(result);
        verify(repository).save(taiKhoan);
    }

    @Test
    void createAccount_ShouldCreateWithEncodedPassword() {
        when(vaiTroRepository.findByMaVaiTro("ADMIN")).thenReturn(Optional.of(vaiTro));
        when(passwordEncoder.encode("123456")).thenReturn("$2a$10$encodedPassword");
        when(repository.save(any(TaiKhoan.class))).thenAnswer(inv -> inv.getArgument(0));

        TaiKhoanRequest req = TaiKhoanRequest.builder()
                .tendangnhap("newadmin")
                .matkhau("123456")
                .hoten("New Admin")
                .email("newadmin@example.com")
                .maVaiTro("ADMIN")
                .trangthai(true)
                .build();

        var result = taiKhoanService.createAccount(req);

        assertEquals("$2a$10$encodedPassword", result.getMatkhau());
        assertEquals("ACTIVE", result.getTrangthai());
    }

    @Test
    void login_ShouldReturnAccountWhenCredentialsValid() {
        when(repository.findByTendangnhap("admin")).thenReturn(Optional.of(taiKhoan));
        when(passwordEncoder.matches("password", "$2a$10$encodedPassword")).thenReturn(true);

        var result = taiKhoanService.login("admin", "password");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getTendangnhap());
    }

    @Test
    void login_ShouldReturnEmptyWhenPasswordInvalid() {
        when(repository.findByTendangnhap("admin")).thenReturn(Optional.of(taiKhoan));
        when(passwordEncoder.matches("wrong", "$2a$10$encodedPassword")).thenReturn(false);

        var result = taiKhoanService.login("admin", "wrong");

        assertTrue(result.isEmpty());
    }

    @Test
    void findByTendangnhap_ShouldReturnAccount() {
        when(repository.findByTendangnhap("admin")).thenReturn(Optional.of(taiKhoan));

        var result = taiKhoanService.findByTendangnhap("admin");

        assertTrue(result.isPresent());
    }
}