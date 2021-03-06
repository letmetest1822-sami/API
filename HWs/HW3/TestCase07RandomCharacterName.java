package apitests.HWs.HW3;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestCase07RandomCharacterName {
    String pagekey = "$2a$10$1aEwwK7/5SoSNyHEkOCfY.jZ/nNK/ohfoqH1Bkl95inAHJMOiTava";


    /*
    Verify all character information
1. Send a get request to /characters. Request includes :
• Header Accept with value application/json
• Query param key with value {{apiKey}}
2. Verify status code 200, content type application/json; charset=utf-8
3. Select name of any random character
4. Send a get request to /characters. Request includes :
• Header Accept with value application/json
• Query param key with value {{apiKey}}
• Query param name with value from step 3
5. Verify that response contains the same character information from step 3. Compare all fields.
     */

    @Test
    public  void randomChar(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", pagekey )
                .when().get("https://www.potterapi.com/v1/characters");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        Random rn = new Random();
        int index = rn.nextInt(196);

        //Character[] character = response.body().as(Character[].class);
        //System.out.println(characters.toString());

        List<String> names = response.path("name");
       // System.out.println(names.toString());

        String nameRandom = names.get(index);



        List<Character> characters = response.body().as(List.class);

        Response response2 = given().accept(ContentType.JSON)
                .and().queryParam("key", pagekey )
                .and().queryParam("name", nameRandom)
                .when().get("https://www.potterapi.com/v1/characters");


        List<Character> characterRand = response2.body().as(List.class);

        assertEquals(characters.get(index), characterRand.get(0));

        //Character characterRandom = response2.body().as(Character.class);

    }



}
