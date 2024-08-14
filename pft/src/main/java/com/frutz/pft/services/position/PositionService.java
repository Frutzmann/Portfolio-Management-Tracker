package com.frutz.pft.services.position;

import com.frutz.pft.dto.PositionDTO;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.Position;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PositionService {
    Position saveOrUpdatePosition(Position position, PositionDTO positionDTO);
    Position createPosition(PositionDTO positionDTO);
    void deletePosition(long id);
    List<Position> getPositionByPortfolioId(long portfolioId);
    List<Position> findPositionByCoin(String coinId);
    Position updatePosition(long id, PositionDTO positionDTO);
}
