package nextstep.subway.section;

import static nextstep.subway.common.SubwayErrorMsg.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import nextstep.subway.line.Line;
import nextstep.subway.line.LineRepository;
import nextstep.subway.station.Station;
import nextstep.subway.station.StationService;

@Service
@RequiredArgsConstructor
public class SectionService {

	private final StationService stationService;
	private final LineRepository lineRepository;

	@Transactional
	public void addSection(Long lineId, SectionCreateRequest sectionRequest) {
		Line findLine = lineRepository.findById(lineId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_LINE.isMessage()));

		Station upStation = stationService.findStationById(sectionRequest.getUpStationId());
		Station downStation = stationService.findStationById(sectionRequest.getDownStationId());

		Section newSection = new Section(upStation, downStation, sectionRequest.getDistance());
		findLine.addSection(newSection);
	}

	@Transactional
	public void deleteSectionById(Long lineId, Long stationId) {
		Line findLine = lineRepository.findById(lineId).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_LINE.isMessage()));
		Station deleteStation = stationService.findStationById(stationId);
		findLine.removeSection(deleteStation);
	}
}
