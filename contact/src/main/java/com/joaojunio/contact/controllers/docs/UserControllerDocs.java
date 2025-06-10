package com.joaojunio.contact.controllers.docs;

import com.joaojunio.contact.data.dto.UserDetailsDTO;
import com.joaojunio.contact.data.dto.UserRequestDTO;
import com.joaojunio.contact.data.dto.UserResponseDTO;
import com.joaojunio.contact.data.dto.UserUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserControllerDocs {

    @Operation(
        summary = "Finding All User entity.",
        description = "Finding All User entity.",
        tags = {"User"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))
                    )
                }
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "201", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "402", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<List<UserResponseDTO>> findAll();

    @Operation(
        summary = "Finding one User entity.",
        description = "Finding one User entity.",
        tags = {"User"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "201", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "402", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<UserResponseDTO> findById(@PathVariable Long id);

    @Operation(
        summary = "Finding one User entity.",
        description = "Finding one User entity.",
        tags = {"User"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "201", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "402", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<UserDetailsDTO> detailsUser(@PathVariable Long id);

    @Operation(
        tags = {"User"},
        summary = "Create a new User entity.",
        description = "Create a new User entity.",
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "201", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<?> create(@RequestBody UserRequestDTO userDTO, HttpServletRequest request);

    @Operation(
        tags = {"User"},
        summary = "Updating one User entity.",
        description = "Updating one User entity.",
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "No Content", responseCode = "201", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<UserResponseDTO> update(@RequestBody UserUpdateRequestDTO userDTO);

    @Operation(
        tags = {"User"},
        summary = "Deleting one User entity.",
        description = "Deleting one User entity.",
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "204", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<?> delete(@PathVariable Long id);
}
