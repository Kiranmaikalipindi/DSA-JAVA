class Device:
    def __init__(self, device_id, name, power_consumption, priority):
        self.device_id = device_id
        self.name = name
        self.power_consumption = power_consumption
        self.priority = priority
        self.status = 'OFF'

    def turn_on(self):
        self.status = 'ON'
        print(f"{self.name} turned ON.")

    def turn_off(self):
        self.status = 'OFF' 
        print(f"{self.name} turned OFF.")

    def __str__(self):
        return f"{self.name} (Priority: {self.priority}, Status: {self.status}, Power: {self.power_consumption}W)"


class Node:
    def __init__(self, device):
        self.device = device
        self.next = None


class PriorityQueue:
    def __init__(self):
        self.head = None

    def is_empty(self):
        return self.head is None    

    def add(self, device):
        new_node = Node(device)
        if self.head is None or self.head.device.priority > device.priority:
            new_node.next = self.head
            self.head = new_node
        else:
            current = self.head
            while current.next and current.next.device.priority <= device.priority:
                current = current.next
            new_node.next = current.next
            current.next = new_node
        print(f"Device {device.name} added with priority {device.priority}")

    def delete(self, device_id):
        current = self.head
        prev = None
        while current and current.device.device_id != device_id:
            prev = current
            current = current.next
        if current:
            if prev:
                prev.next = current.next
            else:
                self.head = current.next
            current.device.turn_off() 
            print(f"Device with ID {device_id} deleted.")
        else:
            print(f"Device with ID {device_id} not found.")

    def display(self):
        current = self.head
        if not current:
            print("No devices in the queue.")
            return
        while current:
            device = current.device
            print(f"ID: {device.device_id}, Name: {device.name}, Priority: {device.priority}, "
                  f"Status: {device.status}, Power: {device.power_consumption}W")
            current = current.next

    def manage_energy(self, max_energy_limit):
        current = self.head
        total_energy = 0
        print(f"\nManaging devices within {max_energy_limit}W energy limit:")
        while current:
            device = current.device
            if total_energy + device.power_consumption <= max_energy_limit:
                device.turn_on()
                total_energy += device.power_consumption
            else:
                device.turn_off()
                print(f"{device.name} turned off to save energy.")
            current = current.next
        print(f"Total energy consumption: {total_energy}W")


class DeviceManager:
    def __init__(self):
        self.priority_queue = PriorityQueue()

    def add_device(self, device):
        self.priority_queue.add(device)

    def delete_device(self, device_id):
        self.priority_queue.delete(device_id)

    def display_devices(self):
        print("\nCurrent Devices in Priority Queue:")
        self.priority_queue.display()

    def manage_energy_consumption(self, max_energy_limit):
        self.priority_queue.manage_energy(max_energy_limit)



if __name__ == "__main__":
    manager = DeviceManager()

    # Adding devices
    device1 = Device(1, "Light", 10, 2)
    device2 = Device(2, "Fan", 20, 3)
    device3 = Device(3, "Heater", 60, 1)

    manager.add_device(device1)
    manager.add_device(device2)
    manager.add_device(device3)

    # Display devices
    manager.display_devices()

    # Manage energy consumption
    manager.manage_energy_consumption(60)

    # Delete a specific device
    manager.delete_device(3)

    # Display devices again
    manager.display_devices()

print(device1)
