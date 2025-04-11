package com.geckotech.assessment.infrastructure.http.rest;

import com.geckotech.assessment.application.service.RecipeService;
import com.geckotech.assessment.domain.repository.exception.SaveFailed;
import com.geckotech.assessment.infrastructure.http.model.ErrorMessage;
import com.geckotech.assessment.infrastructure.http.payload.RecipeFilterPayload;
import com.geckotech.assessment.infrastructure.http.payload.RecipePayload;
import com.geckotech.assessment.infrastructure.http.responseDto.Recipe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/recipe")
@Tag(name = "Recipe", description = "Recipe API")
class RecipeResource {
    private final RecipeService recipeService;
    RecipeResource(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(
        path = "/{id}",
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                description = "Ok",
                responseCode = "200",
                content = {
                    @Content(
                        array = @ArraySchema(
                            schema = @Schema(implementation = Recipe.class)
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class))}
            )
        }
    )
    @Operation(
        summary = "Get Recipe by id",
        description = "Get the Recipe by id"
    )
    public ResponseEntity<?> recipeGetEndpoint(
        @PathVariable(name = "id") Integer id
    ) {
        Recipe result = recipeService.getRecipe(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                description = "Ok",
                responseCode = "201",
                content = {
                    @Content(
                        array = @ArraySchema(
                            schema = @Schema(implementation = RecipePayload.class)
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class))}
            )
        }
    )
    @Operation(
        summary = "Recipe add",
        description = "Update the CV General Information part"
    )
    public ResponseEntity<?> recipeCreateEndpoint(
        @RequestBody
        RecipePayload recipePayload
    ) {
        try {
            recipeService.save(recipePayload);
        } catch (SaveFailed e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(
        path = "/overview",
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                description = "Ok",
                responseCode = "200",
                content = {
                    @Content(
                        array = @ArraySchema(
                            schema = @Schema(implementation = Recipe.class)
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class))}
            )
        }
    )
    @Operation(
        summary = "Recipes Overview",
        description = "Get Recipes overview"
    )
    public ResponseEntity<?> recipeGetOverviewEndpoint() {
        List<Recipe> result = recipeService.getAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping(
        path = "/search",
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                description = "Ok",
                responseCode = "200",
                content = {
                    @Content(
                        array = @ArraySchema(
                            schema = @Schema(implementation = Recipe.class)
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class))}
            )
        }
    )
    @Operation(
        summary = "Recipes Search",
        description = "Search Recipes by any filter"
    )
    public ResponseEntity<?> recipeSearchEndpoint(
        @RequestBody
        RecipeFilterPayload recipeFilterPayload
    ) {
        List<Recipe> result = recipeService.search(recipeFilterPayload);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(
        path = "/{id}",
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                description = "Success",
                responseCode = "204",
                content = {
                    @Content(
                        array = @ArraySchema(
                            schema = @Schema(implementation = Recipe.class)
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {@Content(schema = @Schema(implementation = ErrorMessage.class))}
            )
        }
    )
    @Operation(
        summary = "Delete Recipe",
        description = "Deletes a Recipe by id"
    )
    public ResponseEntity<?> recipeDeleteEndpoint(
        @PathVariable(name = "id") Integer id
    ) {
        recipeService.remove(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
