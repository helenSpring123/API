package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.junit.Test;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {

private int category_id;
private String category_title;
List<CustomResponse> responses;
private int seller_id;
private String seller_name;
private String email;


}
