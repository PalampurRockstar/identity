package com.common.util;

import com.model.dto.UserDto;
import com.model.table.User;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.common.Constants.RECOMMENDED_TERMS_FOR_USERNAME;

@Getter
@Setter
@AllArgsConstructor
public class GenerateUserNames {
    private  static int random(){
        return (new Random()).nextInt(999 - 99 + 1);
    }
    public static List<String> generateUserNameList(User user){
        var input= new ArrayList<>(List.of(RECOMMENDED_TERMS_FOR_USERNAME.replace(" ","").split(",")));
        input.add(user.getUsername());
        System.out.println("input : "+input);
        return combination(input);
    }
    private static List<String> combination(List<String> arr) {
        int n = arr.size();
        List<List<String>> result = IntStream.rangeClosed(0, (1 << n) - 1)
                .boxed()
                .map(i -> {
                    List<String> combination = new ArrayList<>();
                    for (int j = 0; j < n; j++) {
                        if ((i & (1 << j)) != 0) {
                            combination.add(arr.get(j));
                        }
                    }
                    return combination;
                }).collect(Collectors.toList());
        return result.stream().filter(l->l.size()<3).map(r-> String.join("", r)).filter(str -> !str.isEmpty() && !Character.isDigit(str.charAt(0)))
                .map(String::toLowerCase).collect(Collectors.toList());
    }
}
