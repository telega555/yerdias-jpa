package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "characteristics_values") // Updated table name
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    @ManyToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "characteristics_name_id")
    private Option characteristicsName;


    // ... constructors and getters/setters
}
