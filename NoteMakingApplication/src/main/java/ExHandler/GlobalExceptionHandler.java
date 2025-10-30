package ExHandler;

import com.DXCTrainingProject.NoteMakingApplication.DTO.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNoteNotFoundException(NoteNotFoundException e, WebRequest webRequest){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGlobalException(Exception e, WebRequest webRequest){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
