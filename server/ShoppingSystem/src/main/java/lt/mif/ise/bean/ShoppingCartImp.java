package lt.mif.ise.bean;

import lt.mif.ise.error.exception.BadRequestException;
import lt.mif.ise.error.exception.NotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShoppingCartImp implements ShoppingCart{
    private ConcurrentHashMap<String, Integer> _cart = new ConcurrentHashMap<String, Integer>();
    private final Lock lock = new ReentrantLock();

    @Override
    public ConcurrentHashMap<String, Integer> getCart(){
        return _cart;
    }

    @Override
    public Integer addToCart (String productId, Integer amount){
        if (amount < 1)
            throw new BadRequestException(String.format("Product amount is less than 1, it is %s", amount.toString()));

        lock.lock();
        try {
            if (!_cart.containsKey(productId)){
                _cart.put(productId, amount);
                return amount;
            }
            else{
                Integer oldAmount = _cart.get(productId);
                Integer newAmount = oldAmount + amount;
                _cart.replace(productId, oldAmount + amount);
                return newAmount;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer removeFromCart (String productId, Integer amount){
        if (amount < 1)
            throw new BadRequestException(String.format("Product amount is less than 1, it is %d", amount));

        lock.lock();
        try {
            Integer oldAmount = _cart.get(productId);
            if (oldAmount == null)
                throw new NotFoundException(String.format("Product %s not found", productId));

            Integer newAmount = oldAmount - amount;
            if (newAmount < 0)
                throw new BadRequestException(String.format("New amount is less than 0, it is %s", newAmount.toString()));
            else if (newAmount == 0)
                _cart.remove(productId);
            else
                _cart.replace(productId, newAmount);

            return newAmount;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeFromCart (String productId){
        lock.lock();
        try{
            _cart.remove(productId);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void clearCart() {
        lock.lock();
        try {
            _cart.clear();
        } finally {
            lock.unlock();
        }
    }
}
