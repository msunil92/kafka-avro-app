package com.avro.kafkaapp.model;

import lombok.*;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author sunil
 * @project KafkaApp
 * @created 2021/06/19 12:39 AM
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;
    Integer age;
}
