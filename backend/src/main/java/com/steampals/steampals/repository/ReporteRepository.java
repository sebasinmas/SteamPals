package com.steampals.steampals.repository;

import com.steampals.steampals.model.Reporte;
import com.steampals.steampals.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findAllByReportador(Usuario reportador);
    List<Reporte> findAllByReportado(Usuario reportado);
    List<Reporte> findAllByReportadorAndReportado(Usuario reportador, Usuario reportado);

}
