import tensorflow as tf

# 1. Load your existing model
model = tf.keras.models.load_model('faceshield.h5')

# 2. Save it in the 'SavedModel' format
# This will create a folder named 'faceshield_model'
model.export('faceshield_model')

print("Conversion complete! Use the 'faceshield_model' folder in Spring Boot.")