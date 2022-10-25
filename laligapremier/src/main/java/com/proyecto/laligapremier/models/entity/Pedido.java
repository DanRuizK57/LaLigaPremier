package com.proyecto.laligapremier.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Pedido {

    private Long numPedido;

    private Usuario cliente;

    private List<ItemPedido> itemsPedido;

}
