package Model;

public class Record {
	public String accountNumber;
	public float startBalance;
	public float mutation;
	public String description;
	public float endBalance;
	public String invalid;
	public String ref;
	public Record(String ref, String accountNumber, float startBalance, float mutation, String description,
			float endBalance) {
		super();
		this.ref = ref;
		this.accountNumber = accountNumber;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.description = description;
		this.endBalance = endBalance;
	}
	
	public Record(String transactionReference, String accountNumber, String description, float startBalance, float mutation, float endBalance) {
		super();
		this.ref = transactionReference;
		this.accountNumber = accountNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}
	
	public Record() {
		
	}

	public String getTrasactionReference() {
		return ref;
	}

	public void setTrasactionReference(String trasactionReference) {
		this.ref = trasactionReference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public float getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(float startBalance) {
		this.startBalance = startBalance;
	}

	public float getMutation() {
		return mutation;
	}

	public void setMutation(float mutation) {
		this.mutation = mutation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(float endBalance) {
		this.endBalance = endBalance;
	}

	public String getInvalid() {
		return invalid;
	}

	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}

	@Override
	public String toString() {
		return "Record [accountNumber=" + accountNumber + ", startBalance=" + startBalance
				+ ", mutation=" + mutation + ", description=" + description + ", endBalance=" + endBalance
				+ ", invalid=" + invalid + ", ref=" + ref + "]";
	}

}
