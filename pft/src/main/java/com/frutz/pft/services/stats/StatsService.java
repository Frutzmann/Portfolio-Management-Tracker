package com.frutz.pft.services.stats;

import com.frutz.pft.dto.GraphDTO;
import com.frutz.pft.dto.StatsDTO;

public interface StatsService {

    GraphDTO getChartData();

    StatsDTO getStats();
}
