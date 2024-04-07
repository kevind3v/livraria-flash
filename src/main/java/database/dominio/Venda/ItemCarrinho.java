package database.dominio.Venda;

import java.util.Objects;

import database.dominio.Livro.Livro;

public class ItemCarrinho extends Item {
    private Carrinho carrinho;
    
    public ItemCarrinho(Livro livro, int quantidade) {
        super(livro, quantidade, null);
        // TODO Auto-generated constructor stub
    }

    public ItemCarrinho(Livro livro, int quantidade, Carrinho carrinho) {
        super(livro, quantidade,null);

        this.carrinho = carrinho;
    }
    
    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemCarrinho other = (ItemCarrinho) obj;
        
        if(other.getLivro().getId() != this.getLivro().getId())
            return false;
        
        return Objects.equals(carrinho, other.carrinho);
    }

}
