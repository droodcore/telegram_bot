package ru.kortukov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kortukov.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
