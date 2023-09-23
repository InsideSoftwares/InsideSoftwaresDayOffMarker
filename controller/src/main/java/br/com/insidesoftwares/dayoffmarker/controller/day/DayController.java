package br.com.insidesoftwares.dayoffmarker.controller.day;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestPost;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import com.trendyol.jdempotent.core.annotation.JdempotentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Tag(name = "Day", description = "Performs a search on the days registered in the system")
@InsideSoftwaresController
@RequiredArgsConstructor
public class DayController {

    private final DayService dayService;

    @Operation(
            summary = "Get All Day",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findAll(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            InsidePaginationFilterDTO paginationFilter
    ) {
        return dayService.getAllDays(startDate, endDate, paginationFilter);
    }

    @Operation(
            summary = "Link Tag on Day by Id",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Day.Write"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Day.Write')")
    @InsideRequestPost(uri = "/v1/day/{id}/link-tags", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_LINK_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> linkTag(
            @JdempotentRequestPayload @PathVariable Long id,
            @JdempotentRequestPayload @RequestBody LinkTagRequestDTO linkTagRequestDTO
    ) {
        dayService.linkTag(id, linkTagRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @Operation(
            summary = "Unlink Tag on Day by Id",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Write", "DayOff.Day.Write"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Write','DayOff.Day.Write')")
    @InsideRequestPost(uri = "/v1/day/{id}/unlink-tags", httpCode = HttpStatus.ACCEPTED,
            nameCache = {"DAYOFF_MARKER_TAG", "DAYOFF_MARKER_DAY", "DAYOFF_MARKER_WORKING"}
    )
    @JdempotentResource(cachePrefix = "DAYOFF_MARKER_IDP_UNLINK_TAG", ttl = 1)
    public InsideSoftwaresResponseDTO<Void> unlinkTag(
            @JdempotentRequestPayload @PathVariable Long id,
            @JdempotentRequestPayload @RequestBody LinkTagRequestDTO linkTagRequestDTO
    ) {
        dayService.unlinkTag(id, linkTagRequestDTO);
        return InsideSoftwaresResponseDTO.<Void>builder().build();
    }

    @Operation(
            summary = "Get Day by Id",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/{id}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<DayDTO> findById(@PathVariable final Long id) {
        return dayService.getDayByID(id);
    }

    @Operation(
            summary = "Get Day by Date",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/date/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<DayDTO> findDayByDate(
            @PathVariable final LocalDate date,
            @RequestParam(value = "tagID", required = false) Long tagID,
            @RequestParam(value = "tagCode", required = false) String tagCode
    ) {
        return dayService.getDayByDate(date, tagID, tagCode);
    }

    @Operation(
            summary = "Get Day by Tag Id",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/tag/{tagID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysByTag(
            @PathVariable final Long tagID
    ) {
        return dayService.getDaysByTag(tagID);
    }

    @Operation(
            summary = "Get Day by Tag Code",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/tag/code/{codeTag}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysByTag(
            @PathVariable final String codeTag
    ) {
        return dayService.getDaysByTag(codeTag);
    }

    @Operation(
            summary = "Get All Day by Current Month",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/current/month", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysOfCurrentMonth() {
        return dayService.getDaysOfCurrentMonth();
    }

    @Operation(
            summary = "Get All Day By Month",
            security = @SecurityRequirement(name = "DayOffMarker", scopes = {"DayOff.Read", "DayOff.Day.Read"}),
            parameters = {
                    @Parameter(name = "Authorization", required = true, in = ParameterIn.HEADER, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(implementation = String.class, allowableValues = {"pt-BR", "en-US"}))
            }
    )
    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.Day.Read')")
    @InsideRequestGet(uri = "/v1/day/month/{month}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_DAY")
    public InsideSoftwaresResponseDTO<List<DayDTO>> findDaysOfMonth(
            @PathVariable final Month month,
            @RequestParam(value = "year", required = false) Integer year
    ) {
        return dayService.getDaysOfMonth(month, year);
    }

}
