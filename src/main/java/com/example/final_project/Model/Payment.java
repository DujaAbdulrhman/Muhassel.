package com.example.final_project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(40) not null")
    @NotEmpty(message = "the tax payer name can not be empty")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @Column(columnDefinition = "varchar(40) not null")
    @Pattern(regexp = "^(Paid|Pending)$")
    private String status;

    private String paymentId;

    @ManyToOne
    @JsonIgnore
    private TaxPayer taxPayer;

    @OneToOne
    @MapsId
    @JsonIgnore
    private TaxReports taxReports;


}
